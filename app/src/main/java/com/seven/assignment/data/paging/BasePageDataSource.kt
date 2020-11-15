package com.seven.assignment.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.seven.assignment.data.NetworkState
import com.seven.assignment.data.Result
import com.seven.assignment.data.local.MovieDao
import com.seven.assignment.data.models.PaginatedResponse
import com.seven.assignment.data.models.movielist.Movie
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BasePageDataSource<T>() : PageKeyedDataSource<Int, T>() {

    val network = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        fetchData(1) {
            callback.onResult(it.results, null, if (it.page == it.totalPages) null else 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it.results, if (it.page == it.totalPages) null else page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it.results, page - 1)
        }
    }

    abstract fun fetchData(page: Int, callback: (PaginatedResponse<T>) -> Unit)

    protected fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    protected fun processResponse(
        response: Result<PaginatedResponse<T>>,
        page: Int,
        callback: (PaginatedResponse<T>) -> Unit
    ) {
        if (response.status == Result.Status.SUCCESS) {
            val results = response.data!!
            callback(results)
            if (page == 1) {
                if (results.results.count() > 0)
                    postAfterState(NetworkState.LOADED)
                else
                    postAfterState(NetworkState.EMPTY)
            } else {
                postAfterState(NetworkState.LOADED)
            }
        } else if (response.status == Result.Status.ERROR) {
            postError(response.message!!)
        }
    }

    protected suspend fun saveMovies(movies: List<Movie>, movieDao: MovieDao, shelf: MovieShelf) {
        movies.forEach { it.shelf = shelf }
        movieDao.insertMovies(movies)
    }

    protected fun postInitialNetworkStatus(page: Int) {
        if (page == 1)
            postAfterState(NetworkState.FIRST_TIME_LOADING)
        else
            postAfterState(NetworkState.LOADING)
    }

    protected fun postError(message: String) {
        network.postValue(
            NetworkState.error(
                message
            )
        )
    }

    protected fun postAfterState(state: NetworkState) {
        network.postValue(state)
    }
}