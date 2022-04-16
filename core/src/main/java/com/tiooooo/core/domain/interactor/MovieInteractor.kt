package com.tiooooo.core.domain.interactor

import com.tiooooo.core.data.repo.MovieRepositoryImpl
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.domain.usecase.MovieUseCase
import com.tiooooo.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepositoryImpl: MovieRepositoryImpl) : MovieUseCase {
    override fun getMovie(): Flow<Resource<List<Movie>>> {
        return movieRepositoryImpl.getMovies()
    }

    override fun getMovieDetail(id: String): Flow<Resource<Movie>> {
        return movieRepositoryImpl.getMovieDetail(id)
    }

    override fun setMovieFavorite(movie: Movie, newState: Boolean) {
        return movieRepositoryImpl.setMovieFavorite(movie, newState)
    }

    override fun getMovieFavorite(): Flow<List<Movie>> {
        return movieRepositoryImpl.getFavoriteMovie()
    }

    override fun getMovieCasts(id: String): Flow<Resource<List<Casts>>> {
        return movieRepositoryImpl.getMovieCasts(id)
    }

    override suspend fun getMovieNowPlaying(): Flow<Resource<List<Movie>>> =
        movieRepositoryImpl.getMovieNowPlaying()
}