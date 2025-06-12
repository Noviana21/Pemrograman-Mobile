package com.example.movielist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielist.data.local.entities.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
}