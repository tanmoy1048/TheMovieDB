package com.seven.assignment.data.repository

import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import com.google.gson.Gson
import com.seven.assignment.data.Result
import com.seven.assignment.data.local.MovieDao
import com.seven.assignment.data.models.Listing
import com.seven.assignment.data.models.movielist.Movie
import com.seven.assignment.data.paging.*
import com.seven.assignment.data.remote.ApiService
import com.seven.assignment.data.resultLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    gson: Gson
) : BaseDataSource(gson) {

    suspend fun getPopularMovies(page: Int = 1) = getResult {
        apiService.getPopularMovies(page)
    }

    suspend fun getNowPlayingMovies(page: Int = 1) = getResult {
        apiService.getNowPlayingMovies(page)
    }

    suspend fun getTopRatedMovies(page: Int = 1) = getResult {
        apiService.getTopRatedMovies(page)
    }

    suspend fun getUpcomingMovies(page: Int = 1) = getResult {
        apiService.getUpcomingMovies(page)
    }

    fun observeDealDetail(id: Int) = resultLiveData(
        databaseQuery = { movieDao.getMovieDetail(id) },
        networkCall = { getResult { apiService.getMovieDetail(id) } },
        saveCallResult = {
            movieDao.insertMovieDetail(it)
        })

    fun getMovieDetail(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Result.loading())
        val responseStatus = getResult { apiService.getMovieDetail(movieId) }
        if (responseStatus.status == Result.Status.SUCCESS) {
            emit(responseStatus)
        } else if (responseStatus.status == Result.Status.ERROR) {
            emit(Result.error(responseStatus.message!!))
        }
    }

    private fun <T : BasePageDataSource<Movie>> getPaginatedListing(dataSourceFactory: BaseMoviePageDataSourceFactory<T>): Listing<Movie> {
        val liveList = LivePagedListBuilder(
            dataSourceFactory,
            PaginationConfig.pagedListConfig()
        ).build()

        return Listing(
            pagedList = liveList,
            networkState = Transformations.switchMap(dataSourceFactory.liveData) { it.network }
        )
    }

    fun observeRemotePagedPopularMovies(ioCoroutineScope: CoroutineScope): Listing<Movie> {
        val dataSourceFactory =
            BaseMoviePageDataSourceFactory(PopularMoviePageDataSource(this, ioCoroutineScope))
        return getPaginatedListing(dataSourceFactory)
    }

    fun observeRemotePagedTopRatedMovies(ioCoroutineScope: CoroutineScope): Listing<Movie> {
        val dataSourceFactory =
            BaseMoviePageDataSourceFactory(TopRatedMoviePageDataSource(this, ioCoroutineScope))
        return getPaginatedListing(dataSourceFactory)
    }

    fun observeRemotePagedUpcomingMovies(ioCoroutineScope: CoroutineScope): Listing<Movie> {
        val dataSourceFactory =
            BaseMoviePageDataSourceFactory(UpComingMoviePageDataSource(this, ioCoroutineScope))
        return getPaginatedListing(dataSourceFactory)
    }

    fun observeRemotePagedNowPlayingMovies(ioCoroutineScope: CoroutineScope): Listing<Movie> {
        val dataSourceFactory =
            BaseMoviePageDataSourceFactory(NowPlayingMoviePageDataSource(this, ioCoroutineScope))
        return getPaginatedListing(dataSourceFactory)
    }
}