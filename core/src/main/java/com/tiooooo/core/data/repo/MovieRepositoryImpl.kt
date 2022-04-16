package com.tiooooo.core.data.repo

import com.tiooooo.core.data.local.datasource.MovieLocalDataSource
import com.tiooooo.core.data.remote.datasource.movie.MovieRemoteDataSource
import com.tiooooo.core.data.remote.network.ApiResponse
import com.tiooooo.core.data.remote.response.CastItem
import com.tiooooo.core.data.remote.response.ResultsMovie
import com.tiooooo.core.domain.model.Casts
import com.tiooooo.core.domain.model.Movie
import com.tiooooo.core.domain.repo.IMovieRepository
import com.tiooooo.core.utils.AppExecutors
import com.tiooooo.core.utils.NetworkBoundResource
import com.tiooooo.core.utils.Resource
import com.tiooooo.core.utils.map.MovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val appExecutors: AppExecutors,
) : IMovieRepository {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultsMovie>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    MovieMapper.mapListEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<ResultsMovie>>> =
                remoteDataSource.getMovie()


            override suspend fun saveCallResult(data: List<ResultsMovie>) {
                val listMovie = MovieMapper.mapListResponsesToEntities(data)
                localDataSource.insertMovie(listMovie)
            }

        }.asFlow()

    override fun getMovieDetail(id: String): Flow<Resource<Movie>> =
        object : NetworkBoundResource<Movie, ResultsMovie>(appExecutors) {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getDetailMovie(id).map {
//                    MovieMapper.mapMovieEntityToDomain(it)
                    if (it != null) MovieMapper.mapMovieEntityToDomain(it)
                    else Movie(
                        id = "0"
                    )

                }
            }

            override fun shouldFetch(data: Movie?): Boolean =
                data?.genres.isNullOrEmpty()


            override suspend fun createCall(): Flow<ApiResponse<ResultsMovie>> =
                remoteDataSource.getMovieDetail(id)


            override suspend fun saveCallResult(data: ResultsMovie) {
                val movie = MovieMapper.mapMovieResponseToEntity(data)
                localDataSource.updateDetailMovie(movie)
            }

        }.asFlow()

    override fun getMovieCasts(id: String): Flow<Resource<List<Casts>>> =
        object : NetworkBoundResource<List<Casts>, List<CastItem>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Casts>> {
                return localDataSource.getFilmCasts(id).map {
                    MovieMapper.mapCastsEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Casts>?): Boolean =
                data == null || data.isEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<CastItem>>> =
                remoteDataSource.getMovieCast(id)


            override suspend fun saveCallResult(data: List<CastItem>) {
                val listCast = MovieMapper.mapCastsResponseToEntities(data, id)
                localDataSource.insertFilmCasts(listCast)
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovie().map {
            MovieMapper.mapListEntitiesToDomain(it)
        }


    override fun setMovieFavorite(movie: Movie, newState: Boolean) {
        appExecutors.diskIO().execute {
            val movieEntity = MovieMapper.mapDomainToEntity(movie)
            localDataSource.setFavoriteMovie(movieEntity, newState)
        }
    }

    override suspend fun getMovieNowPlaying(): Flow<Resource<List<Movie>>> {
        return remoteDataSource.getMovieNowPlaying()
    }
}