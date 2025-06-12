package com.example.movielist.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movielist.data.local.dao.MovieDao
import com.example.movielist.data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "tmdb_app_db"
    }
}