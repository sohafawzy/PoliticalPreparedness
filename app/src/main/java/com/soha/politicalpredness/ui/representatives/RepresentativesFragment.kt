package com.soha.politicalpredness.ui.representatives

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.soha.politicalpredness.R
import com.soha.politicalpredness.databinding.FragmentRepresentativesBinding
import com.soha.politicalpredness.models.Address
import com.soha.politicalpredness.ui.ViewModelFactory
import com.soha.politicalpredness.utils.Constants.PERMISSIONS_LOCATION
import com.soha.politicalpredness.utils.Constants.REQUEST_LOCATION_PERMISSIONS

class RepresentativesFragment : Fragment() {
    private val representativesViewModel: RepresentativesViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    lateinit var binding: FragmentRepresentativesBinding

    lateinit var adapter: RepresentativesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepresentativesBinding.inflate(inflater, container, false)
        adapter = RepresentativesAdapter(requireContext())

        binding.lifecycleOwner = this@RepresentativesFragment
        binding.representativesVM = representativesViewModel
        binding.rvRepresentatives.adapter = adapter
        binding.btnUseCurrentLocation.setOnClickListener {
            binding.pbRepresentatives.visibility = View.VISIBLE
            if (representativesViewModel.locationUtils.areLocationPermissionsGranted(requireContext()))
                representativesViewModel.locationUtils.getLastLocation(requireContext())
            else
                requestPermissions(PERMISSIONS_LOCATION, REQUEST_LOCATION_PERMISSIONS)

        }

        binding.btnFindMyRepresentatives.setOnClickListener {
            binding.pbRepresentatives.visibility = View.VISIBLE
            val address = Address(
                binding.tvAddressLine1.text.toString(),
                binding.tvAddressLine2.text.toString(), binding.tvCity.text.toString(),
                binding.tvState.selectedItem.toString(), binding.tvZipCode.text.toString()
            )
            representativesViewModel.address.value = address
            representativesViewModel.findRepresentatives(address)
        }
        representativesViewModel.representatives.observe(viewLifecycleOwner, Observer {
            binding.pbRepresentatives.visibility = View.GONE
            binding.mlRepresentatives.getTransition(R.id.swipe).setEnable(true)
            adapter.addRepresentative(it)
        })
        representativesViewModel.error.observe(viewLifecycleOwner, Observer {
            binding.pbRepresentatives.visibility = View.GONE
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                representativesViewModel.locationUtils.getLastLocation(requireContext())
            else {
                binding.pbRepresentatives.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_location_permission_denied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}