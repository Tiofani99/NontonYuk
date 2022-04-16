package com.tiooooo.nontonyuk.ui.dashboard.tvseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tiooooo.core.domain.model.TvSeries
import com.tiooooo.core.domain.usecase.TvSeriesUseCase
import com.tiooooo.core.utils.Resource

class TvSeriesViewModel(private val tvSeriesUseCase: TvSeriesUseCase) : ViewModel() {
    fun fetchTvSeries(): LiveData<Resource<List<TvSeries>>> {
        return tvSeriesUseCase.getTvSeries().asLiveData()
    }
}