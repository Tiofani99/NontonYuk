package com.tiooooo.core.domain.usecase

import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovie(): Flow<Resource<List<Movie>>>
    fun getMovieDetail(id: String): Flow<Resource<Movie>>
    fun setMovieFavorite(movie: Movie, newState: Boolean)
    fun getMovieFavorite(): Flow<List<Movie>>
    fun getMovieCasts(id: String): Flow<Resource<List<Casts>>>
    suspend fun getMovieNowPlaying(): Flow<Resource<List<Movie>>>
}