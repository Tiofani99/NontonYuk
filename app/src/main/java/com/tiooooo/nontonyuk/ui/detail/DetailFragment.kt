package com.tiooooo.nontonyuk.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.tiooooo.core.ui.BaseFragment
import com.tiooooo.core.utils.Routes
import com.tiooooo.core.utils.extensions.hideView
import com.tiooooo.nontonyuk.R
import com.tiooooo.nontonyuk.databinding.FragmentDetailBinding
import com.tiooooo.nontonyuk.ui.base.DetailActivity
import timber.log.Timber


class DetailFragment : BaseFragment<FragmentDetailBinding, DetailActivity>() {

    override fun getLayoutId(): Int = R.layout.fragment_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (parentActivity.route.isNotEmpty()) {
            handleRedirect(parentActivity.route)
        }
        binding.layoutError.btnTryAgain.hideView()
    }

    private fun handleRedirect(route: String) {
        var navDirections: NavDirections? = null
        val errorMessage = "Rute tidak ditemukan"

        when (route) {
            Routes.DETAIL_MOVIE.name -> {
                navDirections = DetailFragmentDirections.actionDetailFragmentToMovieDetailFragment(
                    id = parentActivity.args.id ?: "0"
                )
            }

            Routes.DETAIL_TV_SERIES.name -> {
                navDirections =
                    DetailFragmentDirections.actionDetailFragmentToTvSeriesDetailFragment(
                        id = parentActivity.args.id ?: "0"
                    )
            }
        }

        navDirections?.apply {
            findNavController().navigate(this)
        } ?: kotlin.run {
            Timber.e("error $errorMessage")
            parentActivity.onBackPressed()
        }
    }


}