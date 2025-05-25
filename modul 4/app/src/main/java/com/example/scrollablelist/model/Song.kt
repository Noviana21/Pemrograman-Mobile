package com.example.scrollablelist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val title: String,
    val link: String,
    val photo: String,
    val singer: String,
    val album: String,
    val lyrics: String,
    val desc: String

): Parcelable