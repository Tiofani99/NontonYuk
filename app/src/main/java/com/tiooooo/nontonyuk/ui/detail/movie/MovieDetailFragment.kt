package com.tiooooo.nontonyuk.ui.detail.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.core.utils.Resource
import com.tiooooo.core.utils.extensions.getMessageStatus
import com.tiooooo.core.utils.extensions.hideView
import com.tiooooo.core.utils.extensions.setCollapsing
import com.tiooooo.core.utils.extensions.showView
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.FragmentMovieDetailBinding
import com.tiooooo.nontonyuk.ui.base.DetailActivity
import com.tiooooo.nontonyuk.ui.detail.CastsAdapter
import com.tiooooo.nontonyuk.ui.detail.GenreAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, DetailActivity>() {

    private val viewModel: MovieDetailViewModel by viewModel()
    private val args: MovieDetailFragmentArgs by navArgs()

    private var favorite = false
    private var detailMovie: Movie? = null

    override fun getLayoutId(): Int = R.layout.fragment_movie_detail
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        super.initView()
        parentActivity.setSupportActionBar(binding.toolbar)
        parentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.fetchDetailMovie(args.id ?: "0")
    }

    override fun setSubscribeToLiveData() {
        super.setSubscribeToLiveData()
        viewModel.detailMovie.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        showContent(false)
                    }

                    is Resource.Success -> {
                        if (movie.data?.id != "0") {
                            setMovie(movie.data!!)
                            showContent(true)
                            setCollapsing(
                                movie.data?.title,
                                binding.collapsingToolbar,
                                binding.tvTitle,
                                binding.appbar,
                            )
                        } else {
                            showErrorContent()
                        }
                    }

                    is Resource.Error -> {
                        showErrorContent(movie.message)
                    }
                }
            }
        }

        viewModel.movieCasts.observe(viewLifecycleOwner) { casts ->
            if (casts != null) {
                when (casts) {
                    is Resource.Loading -> {
                        showCast(false)
                    }

                    is Resource.Success -> {
                        showCast(true)
                        initCastsAdapter(casts.data!!)
                    }

                    is Resource.Error -> {
                        showErrorCast(casts.message)
                    }
                }
            }
        }
    }

    private fun initCastsAdapter(data: List<Casts>) {
        if (data.isNullOrEmpty()) showErrorCast(getString(R.string.no_cast))
        else {
            val castAdapter = CastsAdapter(data)
            binding.contentDetail.rvCasts.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = castAdapter
            }
        }
    }

    private fun setMovie(data: Movie) {
        binding.apply {
            ivBackDrop.load(data.createBackdropPath()) {
                crossfade(true)
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
            }
            contentTitle.apply {
                tvTitleTv.text = data.createTitleWithYear()
                tvRatingCount.text = data.createVoteCountToString()

                val genreAdapter = data.genres?.let { GenreAdapter(it) }
                rvGenre.apply {
                    adapter = genreAdapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }
            }
            contentDetail.apply {
                val overview = if (data.overview.isNullOrEmpty()) getString(R.string.no_desc)
                else data.overview
                tvDescDetail.text = overview
                tvReleaseDate.text = data.createDateString()
            }
            setCollapsing(
                data.title,
                collapsingToolbar,
                tvTitle, appbar
            )
        }
        detailMovie = data
        favorite = data.isFavorite!!
        parentActivity.setFavoriteButton(data.isFavorite!!)
    }

    private fun showContent(state: Boolean) {
        binding.appbar.isVisible = state
        binding.nestedScrollView.isVisible = state
        binding.layoutLoading.root.isVisible = !state
    }

    private fun showErrorContent(message: String? = getString(R.string.error_message)) {
        binding.apply {
            appbar.hideView()
            nestedScrollView.hideView()
            layoutLoading.root.hideView()
        }
        binding.layoutError.apply {
            root.showView()
            tvInfo.text = message
            btnTryAgain.setOnClickListener {
                initView()
                root.hideView()
            }
        }
    }

    private fun showCast(state: Boolean) {
        binding.contentDetail.apply {
            shimmerCasts.isVisible = !state
            rvCasts.isVisible = state
        }
    }

    private fun showErrorCast(message: String?) {
        binding.contentDetail.apply {
            shimmerCasts.hideView()
            rvCasts.hideView()
            tvInfoCast.apply {
                showView()
                text = message
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        favorite = !favorite
        if (item.itemId == R.id.action_favorite) {
            detailMovie.let {
                if (it != null) {
                    viewModel.changeMovieFavorite(it, favorite)
                    getMessageStatus(it.title!!, favorite)
                    parentActivity.setFavoriteButton(favorite)
                } else {
                    showErrorContent(getString(R.string.error_message))
                }
            }
        } else if (item.itemId == R.id.action_share) {
            share()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun share() {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder.from(parentActivity).apply {
            setType(mimeType)
            setChooserTitle(getString(R.string.share))
            setText(
                "${detailMovie?.createDeepLink()} ${detailMovie?.createHomepage()}"
            )
            startChooser()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.ivBackDrop.pause()
    }

    override fun onDestroyView() {
        binding.contentDetail.rvCasts.adapter = null
        super.onDestroyView()
    }

}