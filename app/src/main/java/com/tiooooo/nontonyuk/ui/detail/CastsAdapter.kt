package com.tiooooo.nontonyuk.ui.detail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.ItemCastsBinding

class CastsAdapter(private val listCast: List<Casts>) :
    RecyclerView.Adapter<CastsAdapter.CastsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastsViewHolder {
        return CastsViewHolder(
            ItemCastsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CastsViewHolder, position: Int) {
        holder.bindItem(listCast[position])
    }

    override fun getItemCount(): Int = listCast.size


    class CastsViewHolder(val binding: ItemCastsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(casts: Casts) {
            binding.tvItemName.text = casts.name
            if (casts.profilePath == "null" || casts.profilePath.isNullOrEmpty()) {
                when (casts.gender) {
                    1 -> binding.imgItemPhoto.load(R.drawable.ava_1)
                    2 -> binding.imgItemPhoto.load(R.drawable.ava_0)
                    else -> binding.imgItemPhoto.load(R.drawable.ava_0)
                }
            } else {
                binding.imgItemPhoto.load(casts.createPicturePath()) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                    crossfade(true)
                }
            }
        }
    }

}