package com.seven.assignment.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seven.assignment.data.models.movielist.Movie
import com.seven.assignment.databinding.ItemMovieBinding


class MovieAdapter(private val movieClickListener: MovieClickListener) :
    PagedListAdapter<Movie, MovieViewHolder>(object :
        DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater)
        return MovieViewHolder(binding, movieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }
}

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val movieClickListener: MovieClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bindPost(movie: Movie) {
        binding.movie = movie
        binding.itemClickListener = movieClickListener
        binding.executePendingBindings()
    }
}

interface MovieClickListener {
    fun onClick(movie: Movie)
}