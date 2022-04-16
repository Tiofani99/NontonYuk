package com.tiooooo.core.data.remote.network

import com.tiooooo.core.data.remote.response.CastResponse
import com.tiooooo.core.data.remote.response.ResultsItemTv
import com.tiooooo.core.data.remote.response.TvSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvSeriesService {
    @GET("discover/tv")
    suspend fun getTvSeries(
        @Query("api_key") apiKey: String
    ): TvSeriesResponse

    @GET("tv/{tv_id}")
    suspend fun getTvSeriesDetail(
        @Path("tv_id") id: String,
        @Query("api_key") apiKey: String
    ): ResultsItemTv

    @GET("tv/{tv_id}/credits")
    suspend fun getTvCasts(
        @Path("tv_id") id: String,
        @Query("api_key") apiKey: String
    ): CastResponse
}