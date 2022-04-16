package com.tiooooo.core.data.remote.network

import com.tiooooo.core.data.remote.response.CastResponse
import com.tiooooo.core.data.remote.response.MovieResponse
import com.tiooooo.core.data.remote.response.ResultsMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie?")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String
    ): ResultsMovie

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCasts(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String
    ): CastResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String
    ): MovieResponse
}