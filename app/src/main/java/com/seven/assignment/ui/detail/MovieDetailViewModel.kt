package com.seven.assignment.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.seven.assignment.data.repository.MainRepository
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    val movieId = MutableLiveData<Int>()

    private val movieResponse = Transformations.switchMap(movieId) {
        mainRepository.getMovieDetail(it)
    }

    val movieDetail = Transformations.switchMap(movieResponse) {
        liveData { emit(it.data) }
    }

    val networkState = Transformations.switchMap(movieResponse) {
        liveData { emit(it?.status) }
    }
}