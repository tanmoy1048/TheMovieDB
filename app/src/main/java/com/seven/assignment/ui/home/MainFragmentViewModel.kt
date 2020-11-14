package com.seven.assignment.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seven.assignment.data.models.Listing
import com.seven.assignment.data.models.Movie
import com.seven.assignment.data.repository.MainRepository
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    private val pagedNowPlayingMovieResult = MutableLiveData<Listing<Movie>>()
    private val pagedUpComingMovieResult = MutableLiveData<Listing<Movie>>()
    private val pagedTopRatedMovieResult = MutableLiveData<Listing<Movie>>()
    private val pagedPopularMovieResult = MutableLiveData<Listing<Movie>>()

    fun getMovies() {
        pagedNowPlayingMovieResult.postValue(
            mainRepository.observeRemotePagedNowPlayingMovies(
                viewModelScope
            )
        )
        pagedUpComingMovieResult.postValue(
            mainRepository.observeRemotePagedUpcomingMovies(
                viewModelScope
            )
        )
        pagedTopRatedMovieResult.postValue(
            mainRepository.observeRemotePagedTopRatedMovies(
                viewModelScope
            )
        )
        pagedPopularMovieResult.postValue(
            mainRepository.observeRemotePagedPopularMovies(
                viewModelScope
            )
        )
    }


    val nowPlayingMovies = Transformations.switchMap(pagedNowPlayingMovieResult) {
        it.pagedList
    }
    val upComingMovies = Transformations.switchMap(pagedUpComingMovieResult) {
        it.pagedList
    }
    val topRatedMovies = Transformations.switchMap(pagedTopRatedMovieResult) {
        it.pagedList
    }
    val popularMovies = Transformations.switchMap(pagedPopularMovieResult) {
        it.pagedList
    }

    val networkState = Transformations.switchMap(pagedNowPlayingMovieResult) { it.networkState }
}