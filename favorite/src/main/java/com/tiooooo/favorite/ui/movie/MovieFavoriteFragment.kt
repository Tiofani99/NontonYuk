package com.tiooooo.favorite.ui.movie

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.core.utils.extensions.hideView
import com.tiooooo.core.utils.extensions.showView
import com.tiooooo.favorite.R
import com.tiooooo.favorite.databinding.FragmentMovieFavoriteBinding
import com.tiooooo.favorite.ui.FavoriteViewModel
import com.tiooooo.nontonyuk.ui.base.MainActivity
import com.tiooooo.nontonyuk.ui.dashboard.movie.MovieAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFavoriteFragment : BaseFragment<FragmentMovieFavoriteBinding, MainActivity>() {

    override fun getLayoutId(): Int = R.layout.fragment_movie_favorite
    private val viewModel: FavoriteViewModel by viewModel()

    override fun setSubscribeToLiveData() {
        super.setSubscribeToLiveData()
        viewModel.fetchFavoriteMovie().observe(viewLifecycleOwner) {
            showContent(false)
            if (it.isNotEmpty()) {
                showContent(true)
                val movieAdapter = MovieAdapter(it)
                binding.rvMovie.apply {
                    adapter = movieAdapter
                    layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                }
            } else {
                showErrorContent(getString(R.string.you_haven_t_added_a_favorite_movie_yet))
            }
        }
    }

    private fun showContent(state: Boolean) {
        binding.layoutLoading.root.isVisible = !state
        binding.rvMovie.isVisible = state
    }

    private fun showErrorContent(message: String?) {
        binding.apply {
            rvMovie.hideView()
            layoutLoading.root.hideView()
        }
        binding.layoutError.apply {
            root.showView()
            tvInfo.text = message
            btnTryAgain.hideView()
        }
    }

    override fun onDestroyView() {
        binding.rvMovie.adapter = null
        super.onDestroyView()
    }

}