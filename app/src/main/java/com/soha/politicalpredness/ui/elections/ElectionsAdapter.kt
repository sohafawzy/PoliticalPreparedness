package com.soha.politicalpredness.ui.elections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.soha.politicalpredness.databinding.ItemElectionBinding
import com.soha.politicalpredness.models.Election

class ElectionsAdapter : RecyclerView.Adapter<ElectionsAdapter.ElectionViewHolder>() {
    private lateinit var list: List<Election>

    fun addList(elections: List<Election>) {
        list = elections
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder(
            ItemElectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val election = list.get(position)
        holder.bind(election)
        holder.itemView.setOnClickListener {
            it.findNavController()
                .navigate(UpcomingElectionsFragmentDirections.actionGoToVoterInfoFragment(election))
        }

    }

    override fun getItemCount(): Int {
        return if (::list.isInitialized) list.size else 0
    }

    class ElectionViewHolder(private val binding: ItemElectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(election: Election) {
            binding.apply {
                this.election = election
                itemView.setOnClickListener {

                }
            }
        }
    }

}