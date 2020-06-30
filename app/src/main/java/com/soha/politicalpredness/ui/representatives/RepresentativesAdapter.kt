package com.soha.politicalpredness.ui.representatives

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soha.politicalpredness.R
import com.soha.politicalpredness.databinding.ItemRepresentativeBinding
import com.soha.politicalpredness.models.Representative

class RepresentativesAdapter(val context: Context) :
    RecyclerView.Adapter<RepresentativesAdapter.RepresentativeViewHolder>() {

    private lateinit var list: List<Representative>

    fun addRepresentative(representatives: List<Representative>) {
        list = representatives
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepresentativeViewHolder {
        return RepresentativeViewHolder(
            context,
            ItemRepresentativeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepresentativeViewHolder, position: Int) {
        val representative = list.get(position)
        holder.setIsRecyclable(false)
        holder.bind(representative)
    }

    class RepresentativeViewHolder(
        val context: Context,
        private val binding: ItemRepresentativeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val representativeViewModel = RepresentativeViewModel(context)
        fun bind(representative: Representative) {
            binding.apply {
                this.representative = representative
                representativeViewModel.bind(representative)
                representativeVM = representativeViewModel
                representative.official?.photoUrl?.let {
                    Glide.with(context).load(it)
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .circleCrop()
                        .into(ivRepresentativePhoto)
                    return
                }
                ivRepresentativePhoto.setImageDrawable(context.resources.getDrawable(R.drawable.ic_profile))
                if (representative.official?.urls != null && representative.official.urls.isNotEmpty()) {
                    ivWeb.visibility = View.VISIBLE
                } else {
                    ivWeb.visibility = View.GONE
                }

                if (representative.official?.channels != null && representative.official.channels.isNotEmpty()) {
                    if (representative.official.channels.filter { channel -> channel.type == "Facebook" }
                            .isNotEmpty())
                        ivFacebook.visibility = View.VISIBLE
                    else
                        ivFacebook.visibility = View.GONE

                    if (representative.official.channels.filter { channel -> channel.type == "Twitter" }
                            .isNotEmpty())
                        ivTwitter.visibility = View.VISIBLE
                    else
                        ivTwitter.visibility = View.GONE
                } else {
                    ivFacebook.visibility = View.GONE
                    ivTwitter.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (::list.isInitialized) list.size else 0
    }
}