package com.example.scrollablelist.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.scrollablelist.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val title = arguments?.getString("TITLE")
        val photo = arguments?.getString("PHOTO")
        val singer = arguments?.getString("SINGER")
        val album = arguments?.getString("ALBUM")
        val lyrics = arguments?.getString("LYRICS")

        binding.songTitle.text = title
        photo?.let {
            Glide.with(requireContext())
                .load(it)
                .into(binding.songCover)
        }
        binding.songSinger.text = singer
        binding.songAlbum.text = album
        binding.songLyrics.text = lyrics

        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}