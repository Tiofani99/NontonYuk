package com.tiooooo.core.data.local.room

import androidx.room.*
import com.tiooooo.core.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM MOVIE")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MOVIE WHERE id =:id")
    fun getDetailMovie(id: String): Flow<MovieEntity>

    @Query("SELECT * FROM MOVIE WHERE isFavorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDetailMovie(movie: MovieEntity)

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)



}