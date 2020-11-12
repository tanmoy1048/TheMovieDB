package com.seven.assignment.data.repository

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.google.gson.Gson
import com.seven.assignment.data.models.Listing
import com.seven.assignment.data.models.Movie
import com.seven.assignment.data.paging.PaginationConfig
import com.seven.assignment.data.paging.PopularMoviePageDataSourceFactory
import com.seven.assignment.data.remote.ApiService
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val apiService: ApiService,
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

    fun observeRemotePagedPopularMovies(
        ioCoroutineScope: CoroutineScope
    ): Listing<Movie> {
        val dataSourceFactory =
            PopularMoviePageDataSourceFactory(
                this,
                ioCoroutineScope
            )
        val liveList = LivePagedListBuilder(
            dataSourceFactory,
            PaginationConfig.pagedListConfig()
        ).build()

        return Listing(
            pagedList = liveList,
            networkState = Transformations.switchMap(dataSourceFactory.liveData) { it.network }
        )
    }
}