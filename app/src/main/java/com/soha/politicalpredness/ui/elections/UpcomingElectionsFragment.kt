package com.soha.politicalpredness.ui.elections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.soha.politicalpredness.databinding.FragmentElectionsBinding
import com.soha.politicalpredness.ui.ViewModelFactory

class UpcomingElectionsFragment : Fragment() {
    private val electionsViewModel: ElectionsViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private val upcomingElectionAdapter = ElectionsAdapter()
    private val savedElectionAdapter = ElectionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentElectionsBinding.inflate(inflater)
        binding.apply {
            lifecycleOwner = this@UpcomingElectionsFragment
            rvUpcomingElections.adapter = upcomingElectionAdapter
            rvSavedElections.adapter = savedElectionAdapter
        }

        electionsViewModel.elections.observe(viewLifecycleOwner, Observer {
            binding.pbElections.visibility = View.GONE
            upcomingElectionAdapter.addList(it)
        })
        electionsViewModel.savedElections.observe(viewLifecycleOwner, Observer {
            savedElectionAdapter.addList(it)
        })
        electionsViewModel.error.observe(viewLifecycleOwner, Observer {
            binding.pbElections.visibility = View.GONE
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }
}