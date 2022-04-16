package com.tiooooo.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("cast") val cast: List<CastItem>,
    @SerializedName("id") val id: Int,
)

data class CastItem(
    @SerializedName("cast_id") val castId: Int,
    @SerializedName("name") val name: String? = "",
    @SerializedName("profile_path") val profilePath: String? = "",
    @SerializedName("id") val id: Int,
    @SerializedName("gender") val gender: Int? = 0,
)
