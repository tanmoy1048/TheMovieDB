package com.seven.assignment.ui.home

import androidx.lifecycle.*
import com.seven.assignment.data.NetworkState
import com.seven.assignment.data.Status
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

    val popularNetworkState = Transformations.switchMap(pagedPopularMovieResult) { it.networkState }
    val topRatedNetworkState =
        Transformations.switchMap(pagedTopRatedMovieResult) { it.networkState }
    val nowPlayingNetworkState =
        Transformations.switchMap(pagedNowPlayingMovieResult) { it.networkState }
    val upComingNetworkState =
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
        if (nowPlayingStatus?.status == Status.FAILED) {
            return NetworkState.error("${nowPlayingStatus?.msg}")
        } else if (topRatedStatus?.status == Status.FAILED) {
            return NetworkState.error("${topRatedStatus?.msg}")
        } else if (popularStatus?.status == Status.FAILED) {
            return NetworkState.error("${popularStatus?.msg}")
        } else if (upComingStatus?.status == Status.FAILED) {
            return NetworkState.error("${upComingStatus?.msg}")
        } else if (nowPlayingStatus?.status == Status.SUCCESS && topRatedStatus?.status == Status.SUCCESS && popularStatus?.status == Status.SUCCESS && upComingStatus?.status == Status.SUCCESS) {
            return NetworkState.LOADED
        } else if (nowPlayingStatus?.status == Status.FIRST_TIME_RUNNING || topRatedStatus?.status == Status.FIRST_TIME_RUNNING || popularStatus?.status == Status.FIRST_TIME_RUNNING || upComingStatus?.status == Status.FIRST_TIME_RUNNING) {
            return NetworkState.LOADING
        } else {
            return NetworkState.DO_NOTHING
        }
    }
}
