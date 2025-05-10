package com.example.scrollablelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.fragment.app.Fragment
import com.example.scrollablelist.databinding.FragmentHomeBinding
import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var songAdapter: ListSongAdapter
    private val list = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        list.clear()
        list.addAll(getListSong())
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        songAdapter = ListSongAdapter(
            list,
            onSpotifyClick = { link ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)},
            onDetailClick = { title, photo, singer, album, lyrics ->
                val detailFragment = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putString("TITLE", title)
                        putString("PHOTO", photo)
                        putString("SINGER", singer)
                        putString("ALBUM", album)
                        putString("LYRICS", lyrics)
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        binding.rvSong.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = songAdapter
            setHasFixedSize(true)
        }
    }

    private fun getListSong() : ArrayList<Song> {
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataLink = resources.getStringArray(R.array.data_link)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val dataSinger = resources.getStringArray(R.array.data_singer)
        val dataAlbum = resources.getStringArray(R.array.data_album)
        val dataLyrics = resources.getStringArray(R.array.data_lyrics)
        val dataDesc = resources.getStringArray(R.array.data_desc)
        val listSong = ArrayList<Song>()

        for (i in dataTitle.indices) {
            val song = Song(title = dataTitle[i], link = dataLink[i], photo = dataPhoto[i], singer = dataSinger[i], album = dataAlbum[i], lyrics = dataLyrics[i], desc = dataDesc[i])
            listSong.add(song)
        }

        return listSong
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}