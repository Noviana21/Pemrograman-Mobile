package com.example.movielist.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movielist.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double
) {
    fun toDomainMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterPath,
            releaseDate = releaseDate,
            voteAverage = voteAverage
        )
    }

    companion object {
        fun fromDomainMovie(movie: Movie, popularity: Double): MovieEntity {
            return MovieEntity(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage,
                popularity = popularity
            )
        }
    }
}