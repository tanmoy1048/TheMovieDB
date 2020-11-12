package com.seven.assignment.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seven.assignment.R
import com.seven.assignment.data.models.Movie
import kotlinx.android.synthetic.main.item_movie.view.*


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
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, movieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }
}

class MovieViewHolder(
    itemView: View,
    private val movieClickListener: MovieClickListener,
) : RecyclerView.ViewHolder(itemView) {

    private val titleText = itemView.title


    fun bindPost(movie: Movie) {
        with(movie) {
            titleText.text = title
            itemView.setOnClickListener {
                movieClickListener.onClick(movie)
            }
        }
    }
}

interface MovieClickListener {
    fun onClick(movie: Movie)
}