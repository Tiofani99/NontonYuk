package com.tiooooo.core.data.remote.datasource.tvseries

import com.tiooooo.core.data.remote.network.ApiResponse
import com.tiooooo.core.data.remote.network.TvSeriesService
import com.tiooooo.core.data.remote.response.CastItem
import com.tiooooo.core.data.remote.response.ResultsItemTv
import com.tiooooo.core.utils.constant.Constant
import com.tiooooo.core.utils.extensions.getErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class TvSeriesRemoteDataSource(private val service: TvSeriesService) {

    suspend fun getTvSeries(): Flow<ApiResponse<List<ResultsItemTv>>> {
        return flow {
            try {
                val res = service.getTvSeries(Constant.API_KEY)
                val listTv = res.results
                if (listTv.isNotEmpty()) emit(ApiResponse.Success(listTv))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.getErrorMessage()))
                Timber.e(e.getErrorMessage())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvSeriesDetail(id: String): Flow<ApiResponse<ResultsItemTv>> {
        return flow {
            try {
                val res = service.getTvSeriesDetail(id, Constant.API_KEY)
                emit(ApiResponse.Success(res))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.getErrorMessage()))
                Timber.e(e.getErrorMessage())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvSeriesCast(id: String): Flow<ApiResponse<List<CastItem>>> {
        return flow {
            try {
                val res = service.getTvCasts(id, Constant.API_KEY)
                val casts = res.cast
                if (casts.isNotEmpty()) emit(ApiResponse.Success(res.cast))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.getErrorMessage()))
                Timber.e(e.getErrorMessage())
            }
        }.flowOn(Dispatchers.IO)
    }
}