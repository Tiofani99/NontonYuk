package com.tiooooo.core.di

import com.tiooooo.core.data.local.datasource.MovieLocalDataSource
import com.tiooooo.core.data.local.datasource.TvSeriesLocalDataSource
import com.tiooooo.core.data.local.room.AppDatabase
import com.tiooooo.core.data.remote.datasource.movie.MovieRemoteDataSource
import com.tiooooo.core.data.remote.datasource.tvseries.TvSeriesRemoteDataSource
import com.tiooooo.core.data.remote.network.MovieService
import com.tiooooo.core.data.remote.network.TvSeriesService
import com.tiooooo.core.data.repo.MovieRepositoryImpl
import com.tiooooo.core.data.repo.TvSeriesRepositoryImpl
import com.tiooooo.core.utils.AppExecutors
import org.koin.dsl.module

val databaseModule = module {
    factory { get<AppDatabase>().movieDao() }
    factory { get<AppDatabase>().tvSeriesDao() }
    factory { get<AppDatabase>().castsDao() }
    single { createDatabase(this) }
}

val retrofitModule = module {
    single { createHttpLoggingInterceptor() }
    single { createOkHttpClient(get()) }
    single { createRetrofit(get()).create(MovieService::class.java) }
    single { createRetrofit(get()).create(TvSeriesService::class.java) }
}

val repositoryModule = module {
    single { MovieLocalDataSource(get(), get()) }
    single { TvSeriesLocalDataSource(get(), get()) }
    single { MovieRemoteDataSource(get()) }
    single { TvSeriesRemoteDataSource(get()) }
    factory { AppExecutors() }

    single { MovieRepositoryImpl(get(), get(), get()) }
    single { TvSeriesRepositoryImpl(get(), get(), get()) }
}