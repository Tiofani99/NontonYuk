package com.tiooooo.core.data.repo

import com.tiooooo.core.data.local.datasource.TvSeriesLocalDataSource
import com.tiooooo.core.data.remote.datasource.tvseries.TvSeriesRemoteDataSource
import com.tiooooo.core.data.remote.network.ApiResponse
import com.tiooooo.core.data.remote.response.CastItem
import com.tiooooo.core.data.remote.response.ResultsItemTv
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.TvSeries
import com.tiooooo.core.domain.repo.ITvSeriesRepository
import com.tiooooo.core.utils.AppExecutors
import com.tiooooo.core.utils.NetworkBoundResource
import com.tiooooo.core.utils.Resource
import com.tiooooo.core.utils.map.TvSeriesMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TvSeriesRepositoryImpl(
    private val remoteDataSource: TvSeriesRemoteDataSource,
    private val localDataSource: TvSeriesLocalDataSource,
    private val appExecutors: AppExecutors
) : ITvSeriesRepository {
    override fun getTvSeries(): Flow<Resource<List<TvSeries>>> =
        object : NetworkBoundResource<List<TvSeries>, List<ResultsItemTv>>(appExecutors) {
            override fun loadFromDB(): Flow<List<TvSeries>> {
                return localDataSource.getAllTvSeries().map {
                    TvSeriesMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvSeries>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItemTv>>> =
                remoteDataSource.getTvSeries()


            override suspend fun saveCallResult(data: List<ResultsItemTv>) {
                val listSeries = TvSeriesMapper.mapResponsesToEntities(data)
                localDataSource.insertTvSeries(listSeries)
            }

        }.asFlow()


    override fun getTvSeriesDetail(id: String): Flow<Resource<TvSeries>> =
        object : NetworkBoundResource<TvSeries, ResultsItemTv>(appExecutors) {
            override fun loadFromDB(): Flow<TvSeries> {
                return localDataSource.getDetailTvSeries(id).map {
                    if (it != null) TvSeriesMapper.mapDetailTvSeriesEntitiesToDomain(it)
                    else TvSeries(
                        id = "0"
                    )
                }
            }

            override fun shouldFetch(data: TvSeries?): Boolean =
                data?.genres.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<ResultsItemTv>> =
                remoteDataSource.getTvSeriesDetail(id)


            override suspend fun saveCallResult(data: ResultsItemTv) {
                val series = TvSeriesMapper.mapDetailTvSeriesResponsesToEntities(data)
                localDataSource.updateDetailTvSeries(series)
            }

        }.asFlow()


    override fun getTvSeriesCasts(id: String): Flow<Resource<List<Casts>>> =
        object : NetworkBoundResource<List<Casts>, List<CastItem>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Casts>> {
                return localDataSource.getFilmCasts(id).map {
                    TvSeriesMapper.mapCastsEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Casts>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<CastItem>>> =
                remoteDataSource.getTvSeriesCast(id)


            override suspend fun saveCallResult(data: List<CastItem>) {
                val listCast = TvSeriesMapper.mapCastsResponseToEntities(data, id)
                localDataSource.insertFilmCasts(listCast)
            }

        }.asFlow()


    override fun getFavoriteTvSeries(): Flow<List<TvSeries>> =
        localDataSource.getFavoriteTvSeries().map {
            TvSeriesMapper.mapEntitiesToDomain(it)
        }


    override fun setTvSeriesFavorite(tvSeries: TvSeries, newState: Boolean) {
        appExecutors.diskIO().execute {
            val tvSeriesEntity = TvSeriesMapper.mapDomainToEntity(tvSeries)
            localDataSource.setFavoriteTvSeries(tvSeriesEntity, newState)
        }
    }


}