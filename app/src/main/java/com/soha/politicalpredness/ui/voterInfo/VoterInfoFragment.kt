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
import com.soha.politicalpredness.utils.Utils

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
            btnFollow.setOnClickListener {
                voterInfoViewModel.followElection(electionArgs.election)
            }
            tvElectionInformation.setOnClickListener {
                voterInfoViewModel.hasElectionInfo.value?.let { hasElectionInfo ->
                    voterInfoViewModel.voterInfo.value?.state?.get(0)?.electionAdministrationBody?.electionInfoUrl?.let {
                        if (hasElectionInfo) Utils.openUrl(requireContext(), it)
                    }
                }

            }
            tvVotingLocations.setOnClickListener {
                voterInfoViewModel.hasVotingLocations.value?.let { hasVotingLocations ->
                    voterInfoViewModel.voterInfo.value?.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl?.let {
                        if (hasVotingLocations) Utils.openUrl(requireContext(), it)
                    }
                }
            }
            tvBallotLocations.setOnClickListener {
                voterInfoViewModel.hasBallotLocations.value?.let { hasBallotLocations ->
                    voterInfoViewModel.voterInfo.value?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl?.let {
                        if (hasBallotLocations) Utils.openUrl(requireContext(), it)
                    }
                }
            }
        }
        voterInfoViewModel.voterInfo.observe(viewLifecycleOwner, Observer {
            binding.pbVoterInfo.visibility = View.GONE
        })
        voterInfoViewModel.error.observe(viewLifecycleOwner, Observer {
            binding.pbVoterInfo.visibility = View.GONE
            if (!it.contains("Failed to parse address"))
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }
}