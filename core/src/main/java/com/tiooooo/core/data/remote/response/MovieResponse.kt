package com.tiooooo.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<ResultsMovie>,
    @SerializedName("total_results") val totalResults: Int
)


data class ResultsMovie(
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("id") val id: String?= "",
    @SerializedName("vote_count") val voteCount: Int? = 0,
    @SerializedName("genres") val genres: List<GenreListResponse?>? = null,
    @SerializedName("homepage") val homepage: String? = null,
)

