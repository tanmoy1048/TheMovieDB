package com.seven.assignment.data.repository

import android.app.Application
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import com.google.gson.Gson
import com.seven.assignment.data.NetworkState
import com.seven.assignment.data.local.MovieDao
import com.seven.assignment.data.models.Listing
import com.seven.assignment.data.models.movielist.Movie
import com.seven.assignment.data.paging.*
import com.seven.assignment.data.remote.ApiService
import com.seven.assignment.data.resultLiveData
import com.seven.assignment.utils.Utils
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val application: Application,
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

    fun observeMovieDetail(id: Int) = resultLiveData(
        databaseQuery = { movieDao.getMovieDetail(id) },
        networkCall = { getResult { apiService.getMovieDetail(id) } },
        saveCallResult = {
            movieDao.insertMovieDetail(it)
        })

    fun observePagedPopularMovies(
        coroutineScope: CoroutineScope
    ) = if (Utils.isInternetAvailable(application)) observeRemotePagedPopularMovies(coroutineScope)
    else observeLocalPagedMovies(MovieShelf.POPULAR)

    fun observePagedTopRatedMovies(
        coroutineScope: CoroutineScope
    ) = if (Utils.isInternetAvailable(application)) observeRemotePagedTopRatedMovies(coroutineScope)
    else observeLocalPagedMovies(MovieShelf.TOP_RATED)

    fun observePagedNowPlayingMovies(
        coroutineScope: CoroutineScope
    ) = if (Utils.isInternetAvailable(application)) observeRemotePagedNowPlayingMovies(coroutineScope)
    else observeLocalPagedMovies(MovieShelf.NOW_PLAYING)

    fun observePagedUpComingMovies(
        coroutineScope: CoroutineScope
    ) = if (Utils.isInternetAvailable(application)) observeRemotePagedUpcomingMovies(coroutineScope)
    else observeLocalPagedMovies(MovieShelf.UPCOMING)

    private fun observeLocalPagedMovies(shelf: MovieShelf): Listing<Movie> {
        val dataSourceFactory = movieDao.getMovies(shelf)

        val liveList = LivePagedListBuilder(
            dataSourceFactory,
            PaginationConfig.pagedListConfig()
        ).build()

        return Listing(
            pagedList = liveList,
            networkState = liveData {
                emit(NetworkState.LOADED)
            }
        )
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
            BaseMoviePageDataSourceFactory(
                PopularMoviePageDataSource(
                    this,
                    ioCoroutineScope,
                    movieDao
                )
            )
        return getPaginatedListing(dataSourceFactory)
    }

    fun observeRemotePagedTopRatedMovies(ioCoroutineScope: CoroutineScope): Listing<Movie> {
        val dataSourceFactory =
            BaseMoviePageDataSourceFactory(
                TopRatedMoviePageDataSource(
                    this,
                    ioCoroutineScope,
                    movieDao
                )
            )
        return getPaginatedListing(dataSourceFactory)
    }

    fun observeRemotePagedUpcomingMovies(ioCoroutineScope: CoroutineScope): Listing<Movie> {
        val dataSourceFactory =
            BaseMoviePageDataSourceFactory(
                UpComingMoviePageDataSource(
                    this,
                    ioCoroutineScope,
                    movieDao
                )
            )
        return getPaginatedListing(dataSourceFactory)
    }

    fun observeRemotePagedNowPlayingMovies(ioCoroutineScope: CoroutineScope): Listing<Movie> {
        val dataSourceFactory =
            BaseMoviePageDataSourceFactory(
                NowPlayingMoviePageDataSource(
                    this,
                    ioCoroutineScope,
                    movieDao
                )
            )
        return getPaginatedListing(dataSourceFactory)
    }
}