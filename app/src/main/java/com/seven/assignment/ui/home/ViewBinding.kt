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

package com.seven.assignment.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.seven.assignment.BuildConfig
import com.seven.assignment.R
import com.seven.assignment.data.NetworkState
import com.seven.assignment.data.Status
import com.seven.assignment.data.models.Movie
import com.seven.assignment.extension.requestGlideListener


@BindingAdapter("bindMovieImage")
fun bindMovieImage(view: ImageView, movie: Movie) {
    val width = view.resources.getInteger(R.integer.poster_width)
    val glideUrl = "${BuildConfig.BASE_IMAGE_URL}w$width${movie.posterPath}"
    Glide.with(view.context).load(glideUrl)
        .listener(view.requestGlideListener())
        .into(view)
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