package com.example.scrollablelist.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scrollablelist.R
import com.example.scrollablelist.adapter.ListSongAdapter
import com.example.scrollablelist.databinding.FragmentHomeBinding
import com.example.scrollablelist.detail.DetailFragment
import com.example.scrollablelist.model.Song
import com.example.scrollablelist.utils.HomeViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.util.Log

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var songAdapter: ListSongAdapter

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(resources)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeSongList()

        viewModel.loadSongs()
    }

    private fun setupRecyclerView() {
        songAdapter = ListSongAdapter(
        ArrayList(),
            onSpotifyClick = { link ->
                Log.d("HomeFragment", "Spotify button clicked for link: $link")
                Log.e("Intent to Spotify", "Going to $link")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            },
            onDetailClick = { title, photo, singer, album, lyrics ->
                Log.d(
                    "HomeFragment",
                    "Detail button clicked - Title: $title, Singer: $singer, Album: $album"
                )
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

    private fun observeSongList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.songList.collectLatest { list ->
                songAdapter.setData(list)
            }
        }
    }

//    private fun getListSong() : ArrayList<Song> {
//        val dataTitle = resources.getStringArray(R.array.data_title)
//        val dataLink = resources.getStringArray(R.array.data_link)
//        val dataPhoto = resources.getStringArray(R.array.data_photo)
//        val dataSinger = resources.getStringArray(R.array.data_singer)
//        val dataAlbum = resources.getStringArray(R.array.data_album)
//        val dataLyrics = resources.getStringArray(R.array.data_lyrics)
//        val dataDesc = resources.getStringArray(R.array.data_desc)
//        val listSong = ArrayList<Song>()
//        for (i in dataTitle.indices) {
//            val song = Song(
//                title = dataTitle[i],
//                link = dataLink[i],
//                photo = dataPhoto[i],
//                singer = dataSinger[i],
//                album = dataAlbum[i],
//                lyrics = dataLyrics[i],
//                desc = dataDesc[i]
//            )
//            listSong.add(song)
//        }
//
//        return listSong
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}