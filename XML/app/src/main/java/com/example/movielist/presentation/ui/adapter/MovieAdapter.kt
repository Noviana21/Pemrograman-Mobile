package com.example.movielist.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.databinding.ItemMovieBinding
import com.example.movielist.domain.model.Movie

class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnDetail.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
            }
        }

        fun bind(movie: Movie) {
            binding.apply {
                tvMovieTitle.text = movie.title
                tvReleaseDate.text = "Release Date: ${movie.releaseDate}"
                tvVoteAverage.text = "Rating: ${String.format("%.1f", movie.voteAverage)}"
                tvOverview.text = movie.overview

                val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

                Glide.with(itemView.context)
                    .load(imageUrl)
                    .centerCrop()
                    .into(ivPoster)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}