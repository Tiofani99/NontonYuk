package com.tiooooo.core.data.remote.response

import com.google.gson.annotations.SerializedName

// for next feature
data class GenreResponse(
    @SerializedName("genres") val genres: List<GenreListResponse>,
)

data class GenreListResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = "",
)
