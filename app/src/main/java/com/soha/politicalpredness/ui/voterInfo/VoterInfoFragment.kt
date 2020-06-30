package com.soha.politicalpredness.ui.voterInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.soha.politicalpredness.databinding.FragmentVoterInfoBinding
import com.soha.politicalpredness.ui.ViewModelFactory

class VoterInfoFragment : Fragment() {

    val voterInfoViewModel: VoterInfoViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private val electionArgs by navArgs<VoterInfoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.apply {
            lifecycleOwner = this@VoterInfoFragment
            election = electionArgs.election
            voterInfoVM = voterInfoViewModel
            voterInfoViewModel.fetchElectionDetails(electionArgs.election)
        }
        voterInfoViewModel.voterInfo.observe(viewLifecycleOwner, Observer {
            binding.pbVoterInfo.visibility = View.GONE
        })
        voterInfoViewModel.error.observe(viewLifecycleOwner, Observer {
            binding.pbVoterInfo.visibility = View.GONE
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }
}