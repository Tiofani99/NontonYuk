package com.tiooooo.nontonyuk.ui.dashboard.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.domain.model.general.ArgumentWrapper
import com.tiooooo.core.utils.IntentExtra
import com.tiooooo.core.utils.Routes
import com.tiooooo.core.utils.extensions.createImageUrl
import com.tiooooo.core.utils.extensions.putJSON
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.ItemListBinding
import com.tiooooo.nontonyuk.ui.base.DetailActivity

class MovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(movie: Movie) {
            binding.apply {
                tvRating.text = movie.createRatingToString()
                tvTitle.text = movie.createTitleWithYear()
                ivPoster.load(movie.posterPath!!.createImageUrl(false)){
                    crossfade(true)
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                }
            }

            with(itemView) {
                setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(IntentExtra.ROUTE, Routes.DETAIL_MOVIE.name)
                        putJSON(IntentExtra.ARGS, ArgumentWrapper(id = movie.id.toString()))
                    })
                }
            }

        }
    }
}