package com.soha.politicalpredness.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soha.politicalpredness.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLaunchBinding.inflate(inflater)
        return binding.root
    }
}