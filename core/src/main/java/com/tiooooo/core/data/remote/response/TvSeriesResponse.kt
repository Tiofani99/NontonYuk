package com.tiooooo.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class TvSeriesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<ResultsItemTv>,
    @SerializedName("total_results") val totalResults: Int
)

data class ResultsItemTv(
    @SerializedName("first_air_date") val firstAirDate: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("id") val id: String? = "",
    @SerializedName("vote_count") val voteCount: Int? = 0,
    @SerializedName("genres") val genres: List<GenreListResponse?>? = null,
    @SerializedName("homepage") val homepage: String? = null,
)
