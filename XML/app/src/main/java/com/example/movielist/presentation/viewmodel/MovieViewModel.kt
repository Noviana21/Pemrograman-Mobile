package com.example.movielist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.usecase.GetPopularMoviesUseCase
import com.example.movielist.utils.Result
import kotlinx.coroutines.launch

class MovieViewModel (
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _popularMovies = MutableLiveData<Result<List<Movie>>>()
    val popularMovies: LiveData<Result<List<Movie>>> = _popularMovies

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase().collect { result ->
                _popularMovies.value = result
            }
        }
    }
}