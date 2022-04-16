package com.tiooooo.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tiooooo.core.data.local.entity.*
import com.tiooooo.core.utils.converter.Converters

@Database(
    entities = [MovieEntity::class, TvSeriesEntity::class, CastsEntity::class],
    version = 1,
    exportSchema = false

)
@TypeConverters(
    Converters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvSeriesDao(): TvSeriesDao
    abstract fun castsDao(): CastsDao
}