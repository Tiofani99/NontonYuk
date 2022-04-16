package com.tiooooo.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.domain.model.TvSeries
import com.tiooooo.core.domain.usecase.MovieUseCase
import com.tiooooo.core.domain.usecase.TvSeriesUseCase

class FavoriteViewModel(
    private val movieUseCase: MovieUseCase,
    private val tvSeriesUseCase: TvSeriesUseCase
) : ViewModel() {

    fun fetchFavoriteMovie(): LiveData<List<Movie>> =
        movieUseCase.getMovieFavorite().asLiveData()

    fun fetchFavoriteTvSeries(): LiveData<List<TvSeries>> =
        tvSeriesUseCase.getTvSeriesFavorite().asLiveData()


}