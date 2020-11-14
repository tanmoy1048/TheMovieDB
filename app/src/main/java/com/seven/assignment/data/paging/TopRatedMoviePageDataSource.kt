package com.seven.assignment.data.paging

import com.seven.assignment.data.models.PaginatedResponse
import com.seven.assignment.data.models.movielist.Movie
import com.seven.assignment.data.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TopRatedMoviePageDataSource constructor(
    private val dataSource: MainRepository,
    private val scope: CoroutineScope
) : BasePageDataSource<Movie>() {

    override fun fetchData(page: Int, callback: (PaginatedResponse<Movie>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            postInitialNetworkStatus(page)
            val response = dataSource.getTopRatedMovies(page)
            processResponse(response, page, callback)
        }
    }
}