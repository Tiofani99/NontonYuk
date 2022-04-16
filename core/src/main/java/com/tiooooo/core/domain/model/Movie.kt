package com.tiooooo.core.domain.model

import android.os.Parcelable
import com.tiooooo.core.utils.constant.Constant
import com.tiooooo.core.utils.extensions.createImageUrl
import com.tiooooo.core.utils.extensions.toDate
import com.tiooooo.core.utils.extensions.toDateString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: String? = "",
    val overview: String? = "",
    val title: String? = "",
    val posterPath: String? = "",
    val backdropPath: String? = "",
    val releaseDate: String? = "",
    val popularity: Double? = 0.0,
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0,
    val isFavorite: Boolean? = false,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
): Parcelable {
    fun createDateString(): String = releaseDate!!
        .toDate(Constant.DATE_FORMAT)
        .toDateString(Constant.LONG_DATE)

    fun createVoteCountToString(): String =
        "$voteAverage from $voteCount reviews"

    fun createRatingToString(): String = voteAverage.toString()

    fun createTitleWithYear(): String {
        if (releaseDate.isNullOrEmpty()) {
            return title!!
        } else {
            releaseDate.split("-").let {
                return "$title (${it.first()})"
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
        "Hey check this movie $title ${Constant.LINK_TO_DETAIL_MOVIE}$id"
}

