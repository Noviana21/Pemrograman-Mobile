package com.example.movielist.data.local

import android.content.Context
import android.content.SharedPreferences

class MovieAppPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("tmdb_app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_API_KEY = "api_key"
        private const val KEY_DARK_MODE = "dark_mode"
    }

    fun saveApiKey(apiKey: String) {
        sharedPreferences.edit().putString(KEY_API_KEY, apiKey).apply()
    }

    fun getApiKey(): String? {
        return sharedPreferences.getString(KEY_API_KEY, null)
    }

    fun saveDarkModeState(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isDarkMode).apply()
    }

    fun getDarkModeState(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }
}