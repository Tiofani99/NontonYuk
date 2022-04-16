package com.tiooooo.nontonyuk.ui.detail.tvSeries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.TvSeries
import com.tiooooo.core.domain.usecase.TvSeriesUseCase
import com.tiooooo.core.utils.Resource

class TvSeriesDetailViewModel(private val tvSeriesUseCase: TvSeriesUseCase): ViewModel() {
    var detailTvSeries: LiveData<Resource<TvSeries>> = MutableLiveData()
    var tvSeriesCasts: LiveData<Resource<List<Casts>>> = MutableLiveData()

    fun fetchDetailTvSeries(id: String) {
        detailTvSeries = tvSeriesUseCase.getTvSeriesDetail(id).asLiveData()
        tvSeriesCasts = tvSeriesUseCase.getTvSeriesCasts(id).asLiveData()
    }

    fun changeTvSeriesFavorite(tvSeries: TvSeries, state: Boolean){
        tvSeriesUseCase.setTvSeriesFavorite(tvSeries, state)
    }
}