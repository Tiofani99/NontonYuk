package com.tiooooo.nontonyuk.ui.dashboard.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.domain.usecase.MovieUseCase
import com.tiooooo.core.utils.Resource
import com.tiooooo.nontonyuk.service.MovieReminder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val movieUseCase: MovieUseCase,
    private val movieReminder: MovieReminder
) : ViewModel() {
    var movies: LiveData<Resource<List<Movie>>> = MutableLiveData()

    fun fetchMovie() {
        viewModelScope.launch {
            movies = movieUseCase.getMovie().asLiveData()
        }
    }

    fun settingNowPlaying() {
        viewModelScope.launch {
            movieUseCase.getMovieNowPlaying().collectLatest {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("Success ${it.data?.get(0)}")
                        movieReminder.setNowPlayingMovie(it.data?.get(0) ?: Movie())
                    }
                    is Resource.Error -> Timber.e(it.message)
                    is Resource.Loading -> Timber.d("Loading")
                }
            }
        }
    }


}