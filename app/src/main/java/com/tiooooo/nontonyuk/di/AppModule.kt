package com.tiooooo.nontonyuk.di

import com.tiooooo.core.domain.interactor.MovieInteractor
import com.tiooooo.core.domain.interactor.TvSeriesInteractor
import com.tiooooo.core.domain.usecase.MovieUseCase
import com.tiooooo.core.domain.usecase.TvSeriesUseCase
import com.tiooooo.nontonyuk.service.MovieReminder
import com.tiooooo.nontonyuk.ui.dashboard.movie.MovieViewModel
import com.tiooooo.nontonyuk.ui.dashboard.tvseries.TvSeriesViewModel
import com.tiooooo.nontonyuk.ui.detail.movie.MovieDetailViewModel
import com.tiooooo.nontonyuk.ui.detail.tvSeries.TvSeriesDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
    factory<TvSeriesUseCase> { TvSeriesInteractor(get()) }
    single { MovieReminder() }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get(), get()) }
    viewModel { TvSeriesViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { TvSeriesDetailViewModel(get()) }
}