package com.tiooooo.core.data.remote.datasource.movie

import com.bumptech.glide.load.engine.Resource
import com.tiooooo.core.data.remote.network.ApiResponse
import com.tiooooo.core.data.remote.network.MovieService
import com.tiooooo.core.data.remote.response.CastItem
import com.tiooooo.core.data.remote.response.ResultsMovie
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.utils.constant.Constant
import com.tiooooo.core.utils.extensions.getErrorMessage
import com.tiooooo.core.utils.map.MovieMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.KoinComponent
import timber.log.Timber

class MovieRemoteDataSource(private val service: MovieService): KoinComponent {

    suspend fun getMovie(): Flow<ApiResponse<List<ResultsMovie>>> {
        return flow {
            try {
                val res = service.getMovies(Constant.API_KEY)
                val movies = res.results
                if (movies.isNotEmpty()) emit(ApiResponse.Success(res.results))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.getErrorMessage()))
                Timber.e(e.getErrorMessage())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(id: String): Flow<ApiResponse<ResultsMovie>> {
        return flow {
            try {
                val res = service.getMovieDetail(id, Constant.API_KEY)
                emit(ApiResponse.Success(res))

            } catch (e: Exception) {
                emit(ApiResponse.Error(e.getErrorMessage()))
                Timber.e(e.getErrorMessage())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieCast(id: String): Flow<ApiResponse<List<CastItem>>> {
        return flow {
            try {
                val res = service.getMovieCasts(id, Constant.API_KEY)
                val casts = res.cast
                if (casts.isNotEmpty()) emit(ApiResponse.Success(res.cast))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.getErrorMessage()))
                Timber.e(e.getErrorMessage())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieNowPlaying(): Flow<com.tiooooo.core.utils.Resource<List<Movie>>> {
        return flow {
            try {
                emit(com.tiooooo.core.utils.Resource.Loading())
                val res = service.getNowPlaying(Constant.API_KEY)
                emit(com.tiooooo.core.utils.Resource.Success(MovieMapper.mapListResponsesToDomain(res.results)))
            } catch (e: Exception) {
                emit(com.tiooooo.core.utils.Resource.Error(e.getErrorMessage()))
                Timber.e(e.getErrorMessage())
            }
        }.flowOn(Dispatchers.IO)
    }
}