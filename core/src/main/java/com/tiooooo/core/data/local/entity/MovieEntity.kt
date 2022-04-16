package com.tiooooo.core.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tiooooo.core.domain.model.Genre

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "overview") var overview: String? = "",
    @ColumnInfo(name = "title") var title: String? = "",
    @ColumnInfo(name = "poster_path") var posterPath: String? = "",
    @ColumnInfo(name = "backdrop_path") var backdropPath: String? = "",
    @ColumnInfo(name = "release_date") var releaseDate: String? = "",
    @ColumnInfo(name = "popularity") var popularity: Double? = 0.0,
    @ColumnInfo(name = "vote_average") var voteAverage: Double? = 0.0,
    @ColumnInfo(name = "vote_count") var voteCount: Int? = 0,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
    @ColumnInfo(name = "genres") val genres: List<Genre>? = null,
    @ColumnInfo(name = "homepage") val homepage: String? = null,
)
