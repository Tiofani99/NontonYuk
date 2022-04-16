package com.tiooooo.nontonyuk.ui.dashboard.tvseries

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tiooooo.core.domain.model.TvSeries
import com.tiooooo.core.domain.model.general.ArgumentWrapper
import com.tiooooo.core.utils.IntentExtra
import com.tiooooo.core.utils.Routes
import com.tiooooo.core.utils.extensions.createImageUrl
import com.tiooooo.core.utils.extensions.putJSON
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.ItemListBinding
import com.tiooooo.nontonyuk.ui.base.DetailActivity

class TvSeriesAdapter(private val tvSeries: List<TvSeries>) :
    RecyclerView.Adapter<TvSeriesAdapter.TvSeriesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvSeriesViewHolder {
        return TvSeriesViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TvSeriesViewHolder, position: Int) {
        holder.bindItem(tvSeries[position])
    }

    override fun getItemCount(): Int = tvSeries.size

    class TvSeriesViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(tvSeries: TvSeries) {
            binding.apply {
                tvRating.text = tvSeries.createRatingToString()
                tvTitle.text = tvSeries.createTitleWithYear()
                ivPoster.load(tvSeries.posterPath?.createImageUrl(false)) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                }
            }

            with(itemView) {
                setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(IntentExtra.ROUTE, Routes.DETAIL_TV_SERIES.name)
                        putJSON(IntentExtra.ARGS, ArgumentWrapper(id = tvSeries.id.toString()))
                    })
                }
            }

        }
    }
}