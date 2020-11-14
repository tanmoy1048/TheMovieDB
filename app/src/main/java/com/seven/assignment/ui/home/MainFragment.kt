package com.seven.assignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.seven.assignment.data.models.movielist.Movie
import com.seven.assignment.databinding.FragmentMainBinding
import com.seven.assignment.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MainFragment : DaggerFragment(), MovieClickListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var viewModel: MainFragmentViewModel

    private val nowPlayingMovieAdapter = MovieAdapter(this)
    private val topRatedMovieAdapter = MovieAdapter(this)
    private val popularMovieAdapter = MovieAdapter(this)
    private val upComingMovieAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, providerFactory).get(MainFragmentViewModel::class.java)
        return FragmentMainBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
            nowPlayingAdapter = nowPlayingMovieAdapter
            upComingAdapter = upComingMovieAdapter
            topRatedAdapter = topRatedMovieAdapter
            popularAdapter = popularMovieAdapter
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            nowPlayingMovieAdapter.submitList(it)
        })
        viewModel.topRatedMovies.observe(viewLifecycleOwner, {
            topRatedMovieAdapter.submitList(it)
        })
        viewModel.popularMovies.observe(viewLifecycleOwner, {
            popularMovieAdapter.submitList(it)
        })
        viewModel.upComingMovies.observe(viewLifecycleOwner, {
            upComingMovieAdapter.submitList(it)
        })

        viewModel.getMovies()
    }

    override fun onClick(movie: Movie) {

    }

    companion object {
        const val TAG = "MainFragment"
    }
}