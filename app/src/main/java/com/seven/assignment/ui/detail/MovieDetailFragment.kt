package com.seven.assignment.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.seven.assignment.BuildConfig
import com.seven.assignment.R
import com.seven.assignment.extension.requestGlideListener
import com.seven.assignment.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import javax.inject.Inject

class MovieDetailFragment : DaggerFragment() {
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var viewModel: MovieDetailViewModel
    val safeArgs: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, providerFactory).get(MovieDetailViewModel::class.java)
        return com.seven.assignment.databinding.FragmentMovieDetailBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movieId.postValue(safeArgs.movieId)
        subscribeUI()
    }

    private fun setBackDrop(path: String?) {
        path?.let {
            val width = resources.getInteger(R.integer.backdrop_width)
            val glideUrl = "${BuildConfig.BASE_IMAGE_URL}w$width${path}"
            Glide.with(requireContext()).load(glideUrl)
                .listener(imageView.requestGlideListener())
                .into(imageView)
        }
    }

    private fun subscribeUI() {
        viewModel.movieDetail.observe(viewLifecycleOwner, { movieDetail ->
            setBackDrop(movieDetail?.backdropPath)
            title.text = movieDetail?.title
            genre.text = movieDetail?.genres?.joinToString(separator = ",") { it.name }
            movieDetail?.runtime?.let {
                runningTime.text = resources.getQuantityString(R.plurals.minutes, it)
            }
            movieDetail?.voteAverage?.let {
                voteAverage.text = getString(R.string.vote_average, it)
            }
        })
    }
}