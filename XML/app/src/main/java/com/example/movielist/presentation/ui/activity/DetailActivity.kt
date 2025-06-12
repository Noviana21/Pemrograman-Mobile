package com.example.movielist.presentation.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movielist.databinding.ActivityDetailBinding
import com.example.movielist.domain.model.Movie

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_MOVIE)
        }

        movie?.let {

            supportActionBar?.title = it.title

            binding.apply {
                tvDetailTitle.text = it.title
                tvDetailReleaseDate.text = "Release Date: ${it.releaseDate}"
                tvDetailVoteAverage.text = "Rating: ${String.format("%.1f", it.voteAverage)}"
                tvDetailOverview.text = it.overview

                val imageUrl = "https://image.tmdb.org/t/p/w500${it.posterPath}"
                Glide.with(this@DetailActivity)
                    .load(imageUrl)
                    .centerCrop()
                    .into(ivDetailPoster)
            }
        } ?: run {
            Toast.makeText(this, "Film tidak ditemukan.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}