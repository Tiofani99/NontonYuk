package com.tiooooo.core.utils.map

import com.tiooooo.core.data.local.entity.CastsEntity
import com.tiooooo.core.data.local.entity.TvSeriesEntity
import com.tiooooo.core.data.remote.response.CastItem
import com.tiooooo.core.data.remote.response.ResultsItemTv
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.TvSeries

object TvSeriesMapper {
    fun mapResponsesToEntities(input: List<ResultsItemTv>): List<TvSeriesEntity> {
        val listTvSeries = ArrayList<TvSeriesEntity>()
        input.map {
            val tvSeries = TvSeriesEntity(
                id = it.id?:"",
                overview = it.overview.toString(),
                firstAirDate = it.firstAirDate.toString(),
                posterPath = it.posterPath,
                backdropPath = it.backdropPath.toString(),
                popularity = it.popularity ?: 0.0,
                voteAverage = it.voteAverage ?: 0.0,
                name = it.name.toString(),
                voteCount = it.voteCount ?: 0,
                isFavorite = false
            )

            listTvSeries.add(tvSeries)
        }

        return listTvSeries
    }

    fun mapEntitiesToDomain(input: List<TvSeriesEntity>): List<TvSeries> =
        input.map {
            TvSeries(
                id = it.id,
                overview = it.overview,
                firstAirDate = it.firstAirDate,
                posterPath = it.posterPath.toString(),
                backdropPath = it.backdropPath,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                name = it.name,
                voteCount = it.voteCount,
                isFavorite = it.isFavorite,
                genres = it.genres,
                homepage = it.homepage
            )
        }

    fun mapDomainToEntity(input: TvSeries) = TvSeriesEntity(
        id = input.id?: "0",
        overview = input.overview,
        firstAirDate = input.firstAirDate,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        popularity = input.popularity,
        voteAverage = input.voteAverage,
        name = input.name,
        voteCount = input.voteCount,
        isFavorite = input.isFavorite,
        genres = input.genres,
        homepage = input.homepage
    )

    fun mapDetailTvSeriesResponsesToEntities(input: ResultsItemTv): TvSeriesEntity {
        return TvSeriesEntity(
            id = input.id?:"",
            overview = input.overview.toString(),
            firstAirDate = input.firstAirDate.toString(),
            posterPath = input.posterPath,
            backdropPath = input.backdropPath.toString(),
            popularity = input.popularity ?: 0.0,
            voteAverage = input.voteAverage ?: 0.0,
            name = input.name.toString(),
            voteCount = input.voteCount ?: 0,
            isFavorite = false,
            genres = MovieMapper.mapGenreResponseToDomain(input.genres),
            homepage = input.homepage
        )
    }

    fun mapDetailTvSeriesEntitiesToDomain(input: TvSeriesEntity): TvSeries {
        return TvSeries(
            id = input.id,
            overview = input.overview,
            firstAirDate = input.firstAirDate,
            posterPath = input.posterPath.toString(),
            backdropPath = input.backdropPath,
            popularity = input.popularity,
            voteAverage = input.voteAverage,
            name = input.name,
            voteCount = input.voteCount,
            isFavorite = input.isFavorite,
            genres = input.genres,
            homepage = input.homepage
        )
    }


    fun mapCastsResponseToEntities(input: List<CastItem>, filmId: String): List<CastsEntity> {
        val listCast = ArrayList<CastsEntity>()
        input.map {
            val casts = CastsEntity(
                id = it.id,
                filmId = filmId,
                castId = it.id,
                name = it.name,
                profilePath = it.profilePath ?: " "
            )

            listCast.add(casts)
        }

        return listCast
    }

    fun mapCastsEntitiesToDomain(input: List<CastsEntity>): List<Casts> =
        input.map {
            Casts(
                id = it.id,
                name = it.name.toString(),
                profilePath = it.profilePath.toString(),
                filmId = it.filmId,
                castId = it.id,
            )
        }
}