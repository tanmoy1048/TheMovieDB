package com.seven.assignment.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seven.assignment.data.models.Movie

class BaseMoviePageDataSourceFactory<T : BasePageDataSource<Movie>> constructor(
    private val source: T
) : DataSource.Factory<Int, Movie>() {
    val liveData = MutableLiveData<T>()

    override fun create(): DataSource<Int, Movie> {
        liveData.postValue(source)
        return source
    }
}