package com.tiooooo.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tiooooo.core.data.local.entity.CastsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CastsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCasts(reviews: List<CastsEntity>)

    @Query("SELECT * FROM CASTS WHERE film_id = :id ")
    fun getMovieCasts(id: String): Flow<List<CastsEntity>>
}