package com.tiooooo.core.data.local.datasource

import com.tiooooo.core.data.local.entity.CastsEntity
import com.tiooooo.core.data.local.entity.TvSeriesEntity
import com.tiooooo.core.data.local.room.CastsDao
import com.tiooooo.core.data.local.room.TvSeriesDao
import kotlinx.coroutines.flow.Flow

class TvSeriesLocalDataSource(private val tvSeriesDao: TvSeriesDao, private val castsDao: CastsDao) {
    fun getAllTvSeries(): Flow<List<TvSeriesEntity>> = tvSeriesDao.getAllTvSeries()
    fun getDetailTvSeries(id: String): Flow<TvSeriesEntity> = tvSeriesDao.getDetailTvSeries(id)
    fun getFavoriteTvSeries(): Flow<List<TvSeriesEntity>> = tvSeriesDao.getFavoriteTvSeries()
    suspend fun insertTvSeries(tvSeriesList: List<TvSeriesEntity>) =
        tvSeriesDao.insertTvSeries(tvSeriesList)

    suspend fun updateDetailTvSeries(tvSeries: TvSeriesEntity) =
        tvSeriesDao.updateDetailMTvSeries(tvSeries)

    fun setFavoriteTvSeries(tvSeries: TvSeriesEntity, newState: Boolean) {
        tvSeries.isFavorite = newState
        tvSeriesDao.updateFavoriteTvSeries(tvSeries)
    }

    suspend fun insertFilmCasts(listCasts: List<CastsEntity>) = castsDao.insertMovieCasts(listCasts)
    fun getFilmCasts(id: String): Flow<List<CastsEntity>> = castsDao.getMovieCasts(id)
}