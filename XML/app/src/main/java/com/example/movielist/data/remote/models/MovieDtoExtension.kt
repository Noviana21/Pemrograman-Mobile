package com.example.movielist.data.remote.models

import com.example.movielist.domain.model.Movie
import com.example.movielist.data.local.entities.MovieEntity

fun MovieDto.toDomainMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun MovieDto.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        popularity = popularity
    )
}