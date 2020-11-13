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

    fun getMovies() {
        pagedMovieResult.postValue(mainRepository.observeRemotePagedNowPlayingMovies(viewModelScope))
    }

    var pagedMovieResult = MutableLiveData<Listing<Movie>>()

    val movies = Transformations.switchMap(pagedMovieResult) {
        it.pagedList
    }
    val networkState = Transformations.switchMap(pagedMovieResult) { it.networkState }
}