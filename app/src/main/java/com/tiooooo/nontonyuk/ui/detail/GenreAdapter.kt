package com.tiooooo.nontonyuk.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tiooooo.core.domain.model.Genre
import com.tiooooo.nontonyuk.databinding.ItemGenreBinding

class GenreAdapter(private val genres: List<Genre>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindItem(genres[position])
    }

    override fun getItemCount(): Int = genres.size


    class GenreViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(Genre: Genre) {
            binding.tvItemName.text = Genre.name
        }
    }

}