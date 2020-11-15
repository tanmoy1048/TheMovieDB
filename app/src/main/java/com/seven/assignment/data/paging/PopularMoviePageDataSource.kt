package com.seven.assignment.data.paging

import com.seven.assignment.data.local.MovieDao
import com.seven.assignment.data.models.PaginatedResponse
import com.seven.assignment.data.models.movielist.Movie
import com.seven.assignment.data.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PopularMoviePageDataSource constructor(
    private val dataSource: MainRepository,
    private val scope: CoroutineScope,
    private val movieDao: MovieDao
) : BasePageDataSource<Movie>() {

    override fun fetchData(page: Int, callback: (PaginatedResponse<Movie>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            postInitialNetworkStatus(page)
            val response = dataSource.getPopularMovies(page)
            response.data?.results?.let { saveMovies(it, movieDao, MovieShelf.POPULAR) }
            processResponse(response, page, callback)
        }
    }
}