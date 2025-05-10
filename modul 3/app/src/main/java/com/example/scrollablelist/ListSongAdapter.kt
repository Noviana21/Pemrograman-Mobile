package com.example.scrollablelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scrollablelist.databinding.ItemSongBinding

class ListSongAdapter(
    private val listSong: ArrayList<Song>,
    private val onSpotifyClick: (String) -> Unit,
    private val onDetailClick: (String, String, String, String, String) -> Unit)
    : RecyclerView.Adapter<ListSongAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.tvTitle.text = song.title
            binding.tvDesc.text = song.desc
            Glide.with(binding.root.context)
                .load(song.photo)
                .into(binding.imgCover)


            binding.buttonSpotify.setOnClickListener {
                onSpotifyClick(song.link)
            }

            binding.buttonDetail.setOnClickListener {
                onDetailClick(song.title, song.photo, song.singer, song.album, song.lyrics)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listSong.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listSong[position])
    }
    }