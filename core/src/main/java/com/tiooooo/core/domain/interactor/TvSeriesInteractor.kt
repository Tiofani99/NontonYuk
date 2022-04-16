package com.tiooooo.core.domain.interactor

import com.tiooooo.core.data.repo.TvSeriesRepositoryImpl
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.TvSeries
import com.tiooooo.core.domain.usecase.TvSeriesUseCase
import com.tiooooo.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class TvSeriesInteractor(private val tvSeriesRepositoryImpl: TvSeriesRepositoryImpl) :
    TvSeriesUseCase {
    override fun getTvSeries(): Flow<Resource<List<TvSeries>>> {
        return tvSeriesRepositoryImpl.getTvSeries()
    }

    override fun getTvSeriesDetail(id: String): Flow<Resource<TvSeries>> {
        return tvSeriesRepositoryImpl.getTvSeriesDetail(id)
    }

    override fun getTvSeriesCasts(id: String): Flow<Resource<List<Casts>>> {
        return tvSeriesRepositoryImpl.getTvSeriesCasts(id)
    }

    override fun setTvSeriesFavorite(tvSeries: TvSeries, newState: Boolean) {
        return tvSeriesRepositoryImpl.setTvSeriesFavorite(tvSeries, newState)
    }

    override fun getTvSeriesFavorite(): Flow<List<TvSeries>> {
        return tvSeriesRepositoryImpl.getFavoriteTvSeries()
    }

}