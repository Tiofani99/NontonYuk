package com.tiooooo.core.domain.usecase

import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.TvSeries
import com.tiooooo.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TvSeriesUseCase {
    fun getTvSeries(): Flow<Resource<List<TvSeries>>>
    fun getTvSeriesDetail(id: String): Flow<Resource<TvSeries>>
    fun getTvSeriesCasts(id: String): Flow<Resource<List<Casts>>>
    fun setTvSeriesFavorite(tvSeries: TvSeries, newState: Boolean)
    fun getTvSeriesFavorite(): Flow<List<TvSeries>>
}