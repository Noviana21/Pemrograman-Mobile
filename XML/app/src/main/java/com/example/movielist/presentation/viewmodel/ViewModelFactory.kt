package com.example.movielist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movielist.domain.usecase.GetPopularMoviesUseCase

class ViewModelFactory(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(getPopularMoviesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}