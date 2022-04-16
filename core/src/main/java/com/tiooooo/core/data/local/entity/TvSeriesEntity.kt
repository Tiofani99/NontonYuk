package com.tiooooo.core.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tiooooo.core.domain.model.Genre

@Entity(tableName = "tvSeries")
data class TvSeriesEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "first_air_date") var firstAirDate: String?= "",
    @ColumnInfo(name = "overview") var overview: String?= "",
    @ColumnInfo(name = "poster_path") var posterPath: String? = null,
    @ColumnInfo(name = "backdrop_path") var backdropPath: String?= "",
    @ColumnInfo(name = "popularity") var popularity: Double?= 0.0,
    @ColumnInfo(name = "vote_average") var voteAverage: Double? = 0.0,
    @ColumnInfo(name = "name") var name: String?= "",
    @ColumnInfo(name = "vote_count") var voteCount: Int? = 0,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
    @ColumnInfo(name = "genres") val genres: List<Genre>? = null,
    @ColumnInfo(name = "homepage") val homepage: String? = null,
)