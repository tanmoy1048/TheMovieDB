/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.seven.assignment.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.seven.assignment.BuildConfig
import com.seven.assignment.R
import com.seven.assignment.data.NetworkState
import com.seven.assignment.data.Result
import com.seven.assignment.data.Status
import com.seven.assignment.data.models.moviedetail.MovieDetail
import com.seven.assignment.data.models.movielist.Movie
import com.seven.assignment.extension.requestGlideListener

@BindingAdapter("bindMovieImage")
fun bindMovieImage(view: ImageView, movie: Movie) {
    val width = view.resources.getInteger(R.integer.poster_width)
    val glideUrl = "${BuildConfig.BASE_IMAGE_URL}w$width${movie.posterPath}"
    Glide.with(view.context).load(glideUrl)
        .listener(view.requestGlideListener())
        .into(view)
}

@BindingAdapter("bindMovieImage")
fun bindMovieImage(view: ImageView, movie: MovieDetail?) {
    movie?.let {
        val width = view.resources.getInteger(R.integer.backdrop_width)
        val glideUrl = "${BuildConfig.BASE_IMAGE_URL}w$width${movie.backdropPath}"
        Glide.with(view.context).load(glideUrl)
            .listener(view.requestGlideListener())
            .into(view)
    }
}

@BindingAdapter("headingVisibility")
fun headingVisibility(view: View, networkState: NetworkState?) {
    when (networkState?.status) {
        Status.RUNNING -> {
            view.visibility = View.GONE
        }
        Status.SUCCESS, Status.FAILED, Status.SUCCESS_EMPTY -> {
            view.visibility = View.VISIBLE
        }
        Status.FIRST_TIME_RUNNING -> {
            view.visibility = View.GONE
        }
        else -> {
            //Do Nothing
        }
    }
}

@BindingAdapter("customVisibility")
fun customVisibility(progressBar: ProgressBar, networkState: NetworkState?) {
    when (networkState?.status) {
        Status.RUNNING -> {
            progressBar.visibility = View.VISIBLE
        }
        Status.SUCCESS -> {
            progressBar.visibility = View.GONE
        }
        Status.FAILED -> {
            progressBar.visibility = View.GONE
        }
        Status.SUCCESS_EMPTY -> {
            progressBar.visibility = View.GONE
        }
        Status.FIRST_TIME_RUNNING -> {
            progressBar.visibility = View.VISIBLE
        }
        else -> {
            //Do Nothing
        }
    }
}

@BindingAdapter("customVisibility")
fun customVisibility(progressBar: ProgressBar, status: Result.Status?) {
    progressBar.visibility = when (status) {
        Result.Status.SUCCESS -> View.GONE
        Result.Status.ERROR -> View.GONE
        Result.Status.LOADING -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("setDuration")
fun setDuration(view: TextView, duration: Int?) {
    duration?.let {
        view.text = view.resources.getQuantityString(R.plurals.minutes, it, it)
    }
}

@BindingAdapter("voteAverage")
fun voteAverage(view: TextView, duration: Double?) {
    duration?.let {
        view.text = view.resources.getString(R.string.vote_average, it)
    }
}

@BindingAdapter("setGenre")
fun setGenre(view: TextView, movieDetail: MovieDetail?) {
    movieDetail?.genres?.let { genres ->
        view.text = view.resources.getString(
            R.string.genre_list,
            genres.joinToString(separator = ", ") { it.name })
    }
}