package com.example.scrollablelist.home

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrollablelist.R
import com.example.scrollablelist.model.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import android.util.Log

class HomeViewModel(private val resources: Resources) : ViewModel() {
    //MutableStateFlow untuk menyimpan list item(private)
    private val _songList = MutableStateFlow<List<Song>>(emptyList())

    //public read-only access untuk mengobservasi list item
    val songList: StateFlow<List<Song>> get() = _songList

    //function yang mengembalikan data list chara
    private fun getSongFlow(): Flow<List<Song>> = flow {
        //mengambil data dari Resources
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataLink = resources.getStringArray(R.array.data_link)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val dataSinger = resources.getStringArray(R.array.data_singer)
        val dataAlbum = resources.getStringArray(R.array.data_album)
        val dataLyrics = resources.getStringArray(R.array.data_lyrics)
        val dataDesc = resources.getStringArray(R.array.data_desc)

        //membuat list
        val listSong = ArrayList<Song>()
        for (i in dataTitle.indices) {
            val song = Song(
                title = dataTitle[i],
                link = dataLink[i],
                photo = dataPhoto[i],
                singer = dataSinger[i],
                album = dataAlbum[i],
                lyrics = dataLyrics[i],
                desc = dataDesc[i]
            )
            listSong.add(song)
        }
        //mengirim item ke collector
        emit(listSong)
    }
    //function load item
    fun loadSongs() {
        viewModelScope.launch {
            collectSongs()
        }
    }

    private suspend fun collectSongs() {
        getSongFlow()
            .onStart {
                _songList.value = emptyList()
            }
            .collect { songs ->
                Log.d("HomeViewModel", "Songs loaded: ${songs.size} items")
                _songList.value = songs
            }
    }

}