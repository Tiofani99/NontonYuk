package com.tiooooo.core.data.local.room

import androidx.room.*
import com.tiooooo.core.data.local.entity.TvSeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvSeriesDao {
    @Query("SELECT * FROM tvSeries")
    fun getAllTvSeries(): Flow<List<TvSeriesEntity>>

    @Query("SELECT * FROM tvSeries where id=:id")
    fun getDetailTvSeries(id: String): Flow<TvSeriesEntity>

    @Query("SELECT * FROM tvSeries where isFavorite = 1")
    fun getFavoriteTvSeries(): Flow<List<TvSeriesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvSeries(tvSeries: List<TvSeriesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDetailMTvSeries(tvSeries: TvSeriesEntity)

    @Update
    fun updateFavoriteTvSeries(tvSeries: TvSeriesEntity)

}