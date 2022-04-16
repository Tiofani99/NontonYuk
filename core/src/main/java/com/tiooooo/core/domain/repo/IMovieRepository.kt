package com.tiooooo.core.domain.repo

import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>
    fun getMovieDetail(id: String): Flow<Resource<Movie>>
    fun getMovieCasts(id: String): Flow<Resource<List<Casts>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setMovieFavorite(movie: Movie, newState: Boolean)
    suspend fun getMovieNowPlaying(): Flow<Resource<List<Movie>>>
}