package com.tiooooo.favorite.ui.tvseries

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.core.utils.extensions.hideView
import com.tiooooo.core.utils.extensions.showView
import com.tiooooo.favorite.R
import com.tiooooo.favorite.databinding.FragmentTvSeriesFavoriteBinding
import com.tiooooo.favorite.ui.FavoriteViewModel
import com.tiooooo.nontonyuk.ui.base.MainActivity
import com.tiooooo.nontonyuk.ui.dashboard.tvseries.TvSeriesAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class TvSeriesFavoriteFragment : BaseFragment<FragmentTvSeriesFavoriteBinding, MainActivity>() {


    override fun getLayoutId(): Int = R.layout.fragment_tv_series_favorite
    private val viewModel: FavoriteViewModel by viewModel()

    override fun setSubscribeToLiveData() {
        super.setSubscribeToLiveData()
        viewModel.fetchFavoriteTvSeries().observe(viewLifecycleOwner) {
            showContent(false)
            if (it.isNotEmpty()) {
                showContent(true)
                val tvSeriesAdapter = TvSeriesAdapter(it)
                binding.rvTvSeries.apply {
                    adapter = tvSeriesAdapter
                    layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                }
            } else {
                showErrorContent(getString(R.string.you_haven_t_added_a_favorite_tv_yet))
            }
        }
    }

    private fun showContent(state: Boolean) {
        binding.layoutLoading.root.isVisible = !state
        binding.rvTvSeries.isVisible = state
    }

    private fun showErrorContent(message: String?) {
        binding.apply {
            rvTvSeries.hideView()
            layoutLoading.root.hideView()
        }
        binding.layoutError.apply {
            root.showView()
            tvInfo.text = message
            btnTryAgain.hideView()
        }
    }

    override fun onDestroyView() {
        binding.rvTvSeries.adapter = null
        super.onDestroyView()
    }
}