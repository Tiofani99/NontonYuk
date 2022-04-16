package com.tiooooo.nontonyuk.ui.dashboard.movie

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.core.utils.DataState
import com.tiooooo.core.utils.Resource
import com.tiooooo.core.utils.extensions.hideView
import com.tiooooo.core.utils.extensions.showView
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.FragmentMovieBinding
import com.tiooooo.nontonyuk.ui.base.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel


class MovieFragment : BaseFragment<FragmentMovieBinding, MainActivity>() {
    override fun getLayoutId(): Int = R.layout.fragment_movie
    private val viewModel: MovieViewModel by viewModel()

    override fun setSubscribeToLiveData() {
        super.setSubscribeToLiveData()
        viewModel.settingNowPlaying()
        viewModel.fetchMovie()
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> setDataState(DataState.LOADING)
                    is Resource.Success -> {
                        setDataState(DataState.SUCCESS)
                        setupAdapter(movies.data!!)
                    }
                    is Resource.Error -> setDataState(DataState.ERROR, movies.message)
                }
            }


        }
    }

    private fun setDataState(state: DataState, message: String? = null) {
        binding.apply {
            when (state) {
                DataState.LOADING -> {
                    rvMovie.hideView()
                    layoutLoading.root.showView()
                    layoutError.root.hideView()
                }
                DataState.SUCCESS -> {
                    layoutLoading.root.hideView()
                    layoutError.root.hideView()
                    rvMovie.showView()
                }
                DataState.ERROR -> {
                    rvMovie.hideView()
                    layoutLoading.root.hideView()
                    layoutError.apply {
                        root.showView()
                        tvInfo.text = message
                        btnTryAgain.setOnClickListener {
                            root.hideView()
                            layoutLoading.root.showView()
                            initView()
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapter(movies: List<Movie>) {
        val movieAdapter = MovieAdapter(movies)
        binding.rvMovie.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = movieAdapter
        }
    }

    override fun onDestroyView() {
        binding.rvMovie.adapter = null
        super.onDestroyView()
    }
}