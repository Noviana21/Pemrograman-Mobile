package com.example.movielist.data.repository

import com.example.movielist.data.local.dao.MovieDao
import com.example.movielist.data.remote.api.TmdbApiService
import com.example.movielist.data.remote.models.toDomainMovie
import com.example.movielist.data.remote.models.toMovieEntity
import com.example.movielist.domain.model.Movie
import com.example.movielist.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

interface MovieRepository {
    fun getPopularMovies(): Flow<Result<List<Movie>>>
}

class MovieRepositoryImpl(
    private val apiService: TmdbApiService,
    private val movieDao: MovieDao,
    private val apiKey: String
) : MovieRepository {

    override fun getPopularMovies(): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)

        val cachedMovies = movieDao.getAllMovies().map { it.toDomainMovie() }
        if (cachedMovies.isNotEmpty()) {
            emit(Result.Success(cachedMovies))
        }

        try {
            val response = apiService.getPopularMovies(apiKey = apiKey)
            if (response.isSuccessful) {
                val movieDtos = response.body()?.results ?: emptyList()
                val domainMovies = movieDtos.map { it.toDomainMovie() }

                movieDao.clearAllMovies()
                movieDao.insertAllMovies(movieDtos.map { it.toMovieEntity() })

                emit(Result.Success(domainMovies))
            } else {
                emit(Result.Error(Exception("API Error: ${response.code()} ${response.message()}")))
            }
        } catch (e: HttpException) {
            emit(Result.Error(Exception("Network Error (HTTP ${e.code()}): ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.Error(Exception("No Internet Connection or API Timeout: ${e.message}")))
        } catch (e: Exception) {
            emit(Result.Error(Exception("An unexpected error occurred: ${e.localizedMessage}")))
        }
    }
}