package com.tiooooo.core.data.local.datasource

import com.tiooooo.core.data.local.entity.CastsEntity
import com.tiooooo.core.data.local.entity.MovieEntity
import com.tiooooo.core.data.local.room.CastsDao
import com.tiooooo.core.data.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val movieDao: MovieDao, private val castsDao: CastsDao) {
    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()
    fun getDetailMovie(id: String): Flow<MovieEntity> = movieDao.getDetailMovie(id)
    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()
    suspend fun insertMovie(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)
    suspend fun updateDetailMovie(movie: MovieEntity) = movieDao.updateDetailMovie(movie)
    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }

    suspend fun insertFilmCasts(listCasts: List<CastsEntity>) = castsDao.insertMovieCasts(listCasts)
    fun getFilmCasts(id: String): Flow<List<CastsEntity>> = castsDao.getMovieCasts(id)

}
