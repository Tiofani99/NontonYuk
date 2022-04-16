package com.tiooooo.core.utils.map

import com.tiooooo.core.data.local.entity.CastsEntity
import com.tiooooo.core.data.local.entity.MovieEntity
import com.tiooooo.core.data.remote.response.CastItem
import com.tiooooo.core.data.remote.response.GenreListResponse
import com.tiooooo.core.data.remote.response.ResultsMovie
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.Genre
import com.tiooooo.core.domain.model.Movie

object MovieMapper {
    fun mapListResponsesToEntities(input: List<ResultsMovie>): List<MovieEntity> {
        val listMovie = ArrayList<MovieEntity>()
        input.map { listMovie.add(mapMovieResponseToEntity(it)) }
        return listMovie
    }

    fun mapListResponsesToDomain(input: List<ResultsMovie>): List<Movie> {
        val listMovie = ArrayList<Movie>()
        input.map { listMovie.add(mapMovieResponseToDomain(it)) }
        return listMovie
    }

    fun mapListEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map { mapMovieEntityToDomain(it) }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id?:"",
        title = input.title,
        overview = input.overview,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        releaseDate = input.releaseDate,
        popularity = input.popularity,
        voteAverage = input.voteAverage,
        voteCount = input.voteCount,
        isFavorite = input.isFavorite?:false,
        genres = input.genres,
        homepage = input.homepage
    )

    fun mapMovieResponseToEntity(input: ResultsMovie): MovieEntity {
        return MovieEntity(
            id = input.id?:"",
            title = input.title,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            releaseDate = input.releaseDate,
            popularity = input.popularity,
            overview = input.overview,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            isFavorite = false,
            genres = mapGenreResponseToDomain(input.genres),
            homepage = input.homepage
        )
    }

    fun mapMovieResponseToDomain(input: ResultsMovie): Movie {
        return Movie(
            id = input.id?:"",
            title = input.title,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            releaseDate = input.releaseDate,
            popularity = input.popularity,
            overview = input.overview,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            isFavorite = false,
            genres = mapGenreResponseToDomain(input.genres),
            homepage = input.homepage
        )
    }

    fun mapMovieEntityToDomain(input: MovieEntity): Movie {
        return Movie(
            id = input.id,
            overview = input.overview.toString(),
            title = input.title.toString(),
            posterPath = input.posterPath.toString(),
            backdropPath = input.backdropPath.toString(),
            releaseDate = input.releaseDate.toString(),
            popularity = input.popularity ?: 0.0,
            voteAverage = input.voteAverage ?: 0.0,
            voteCount = input.voteCount ?: 0,
            isFavorite = input.isFavorite,
            genres = input.genres,
            homepage = input.homepage.toString()
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
                profilePath = it.profilePath.toString(),
                gender = it.gender
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
                gender = it.gender
            )
        }


    val color = arrayOf(
        "#995A3761",
        "#99C74375",
        "#99FEA904",
        "#99A01212",
        "#996D4773",
        "#990081B8",
        "#99E0457F",
        "#99B6DF82"
    )

    fun mapGenreResponseToDomain(input: List<GenreListResponse?>?): List<Genre> {
        val genres = ArrayList<Genre>()
        input?.map {
            val genre = Genre(
                id = it!!.id,
                name = it.name.toString(),
                backgroundColor = color.random()
            )

            genres.add(genre)
        }
        return genres
    }
}