package com.tiooooo.nontonyuk.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.domain.usecase.MovieUseCase
import com.tiooooo.core.utils.Resource

class MovieDetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    var detailMovie: LiveData<Resource<Movie>> = MutableLiveData()
    var movieCasts: LiveData<Resource<List<Casts>>> = MutableLiveData()

    fun fetchDetailMovie(id: String) {
        detailMovie = movieUseCase.getMovieDetail(id).asLiveData()
        movieCasts = movieUseCase.getMovieCasts(id).asLiveData()
    }

    fun changeMovieFavorite(movie: Movie, state: Boolean){
        movieUseCase.setMovieFavorite(movie, state)
    }
}