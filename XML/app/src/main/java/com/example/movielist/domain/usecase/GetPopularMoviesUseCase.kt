package com.example.movielist.domain.usecase

import com.example.movielist.domain.model.Movie
import com.example.movielist.data.repository.MovieRepositoryImpl
import com.example.movielist.utils.Result
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase (
    private val movieRepository: MovieRepositoryImpl
) {
    operator fun invoke(): Flow<Result<List<Movie>>> {
        return movieRepository.getPopularMovies()
    }
}