package com.seven.assignment.ui.home

import androidx.lifecycle.*
import com.seven.assignment.data.NetworkState
import com.seven.assignment.data.Status
import com.seven.assignment.data.models.Listing
import com.seven.assignment.data.models.movielist.Movie
import com.seven.assignment.data.repository.MainRepository
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    private val pagedNowPlayingMovieResult = MutableLiveData<Listing<Movie>>()
    private val pagedUpComingMovieResult = MutableLiveData<Listing<Movie>>()
    private val pagedTopRatedMovieResult = MutableLiveData<Listing<Movie>>()
    private val pagedPopularMovieResult = MutableLiveData<Listing<Movie>>()
    val showSwipeToRefresh = MutableLiveData<Boolean>()
    private var loaded = false


    fun getMovies() {
        showSwipeToRefresh.postValue(false)
        if (loaded) {
            return
        }
        pagedNowPlayingMovieResult.postValue(
            mainRepository.observePagedNowPlayingMovies(
                viewModelScope
            )
        )
        pagedUpComingMovieResult.postValue(
            mainRepository.observePagedUpComingMovies(
                viewModelScope
            )
        )
        pagedTopRatedMovieResult.postValue(
            mainRepository.observePagedTopRatedMovies(
                viewModelScope
            )
        )
        pagedPopularMovieResult.postValue(
            mainRepository.observePagedPopularMovies(
                viewModelScope
            )
        )
    }

    fun onRefresh() {
        loaded = false
        getMovies()
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

    private val popularNetworkState =
        Transformations.switchMap(pagedPopularMovieResult) { it.networkState }
    private val topRatedNetworkState =
        Transformations.switchMap(pagedTopRatedMovieResult) { it.networkState }
    private val nowPlayingNetworkState =
        Transformations.switchMap(pagedNowPlayingMovieResult) { it.networkState }
    private val upComingNetworkState =
        Transformations.switchMap(pagedUpComingMovieResult) { it.networkState }

    private val combinedNetworkStateData = MediatorLiveData<CombinedStatus>().apply {
        addSource(nowPlayingNetworkState) {
            value = CombinedStatus(
                nowPlayingStatus = it,
                topRatedStatus = topRatedNetworkState.value,
                popularStatus = popularNetworkState.value,
                upComingStatus = upComingNetworkState.value
            )
        }
        addSource(upComingNetworkState) {
            value = CombinedStatus(
                nowPlayingStatus = nowPlayingNetworkState.value,
                topRatedStatus = topRatedNetworkState.value,
                popularStatus = popularNetworkState.value,
                upComingStatus = it
            )
        }
        addSource(topRatedNetworkState) {
            value = CombinedStatus(
                nowPlayingStatus = nowPlayingNetworkState.value,
                topRatedStatus = it,
                popularStatus = popularNetworkState.value,
                upComingStatus = upComingNetworkState.value
            )
        }
        addSource(popularNetworkState) {
            value = CombinedStatus(
                nowPlayingStatus = nowPlayingNetworkState.value,
                topRatedStatus = topRatedNetworkState.value,
                popularStatus = it,
                upComingStatus = upComingNetworkState.value
            )
        }
    }

    val combinedNetworkState = Transformations.switchMap(combinedNetworkStateData) {
        if (it.nowPlayingStatus != null && it.popularStatus != null && it.topRatedStatus != null && it.upComingStatus != null) {
            val status = it.getStatus()
            if (status == NetworkState.LOADED) {
                loaded = true
            }
            liveData { emit(it.getStatus()) }
        } else {
            liveData<NetworkState?> { emit(null) }
        }
    }
}

data class CombinedStatus(
    var nowPlayingStatus: NetworkState? = null,
    var topRatedStatus: NetworkState? = null,
    var popularStatus: NetworkState? = null,
    var upComingStatus: NetworkState? = null
) {
    fun getStatus(): NetworkState? {
        return if (nowPlayingStatus?.status == Status.FAILED) {
            NetworkState.error("${nowPlayingStatus?.msg}")
        } else if (topRatedStatus?.status == Status.FAILED) {
            NetworkState.error("${topRatedStatus?.msg}")
        } else if (popularStatus?.status == Status.FAILED) {
            NetworkState.error("${popularStatus?.msg}")
        } else if (upComingStatus?.status == Status.FAILED) {
            NetworkState.error("${upComingStatus?.msg}")
        } else if (nowPlayingStatus?.status == Status.SUCCESS && topRatedStatus?.status == Status.SUCCESS && popularStatus?.status == Status.SUCCESS && upComingStatus?.status == Status.SUCCESS) {
            NetworkState.LOADED
        } else if (nowPlayingStatus?.status == Status.FIRST_TIME_RUNNING || topRatedStatus?.status == Status.FIRST_TIME_RUNNING || popularStatus?.status == Status.FIRST_TIME_RUNNING || upComingStatus?.status == Status.FIRST_TIME_RUNNING) {
            NetworkState.LOADING
        } else {
            NetworkState.DO_NOTHING
        }
    }
}
