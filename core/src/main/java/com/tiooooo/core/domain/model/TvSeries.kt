package com.tiooooo.core.domain.model

import com.tiooooo.core.utils.constant.Constant
import com.tiooooo.core.utils.extensions.createImageUrl
import com.tiooooo.core.utils.extensions.toDate
import com.tiooooo.core.utils.extensions.toDateString

data class TvSeries(
    val id: String? = "",
    val firstAirDate: String? = "",
    val overview: String? = "",
    val posterPath: String? = "",
    val backdropPath: String? = "",
    val popularity: Double? = 0.0,
    val voteAverage: Double? = 0.0,
    val name: String? = "",
    val voteCount: Int? = 0,
    val isFavorite: Boolean = false,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
) {
    fun createDateString(): String = firstAirDate!!
        .toDate(Constant.DATE_FORMAT)
        .toDateString(Constant.LONG_DATE)

    fun createVoteCountToString(): String =
        "$voteAverage from $voteCount reviews"

    fun createRatingToString(): String = voteAverage.toString()

    fun createTitleWithYear(): String {
        if (firstAirDate == "null") {
            return name!!
        } else {
            firstAirDate?.split("-").let {
                return "$name (${it?.first()})"
            }
        }
    }

    fun createBackdropPath(): String =
        if (backdropPath?.isEmpty()!!) {
            posterPath!!.createImageUrl()
        } else {
            backdropPath.createImageUrl()
        }

    fun createHomepage(): String =
        if (homepage != null) "\n" +
                "You can visit the site below for more details $homepage"
        else ""

    fun createDeepLink(): String =
        "Hey check this tv $name ${Constant.LINK_TO_DETAIL_TV}$id"
}
