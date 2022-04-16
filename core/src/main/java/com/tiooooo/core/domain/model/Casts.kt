package com.tiooooo.core.domain.model

import com.tiooooo.core.utils.constant.Constant


data class Casts(
    val id: Int,
    val filmId: String,
    val name: String,
    val profilePath: String,
    val castId: Int,
    val gender: Int? = 0,
) {
    fun createPicturePath(): String = Constant.BASE_IMAGE_500 + profilePath
}
