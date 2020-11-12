package com.seven.assignment.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.seven.assignment.data.models.Movie
import com.seven.assignment.data.repository.MainRepository
import kotlinx.coroutines.CoroutineScope

class PopularMoviePageDataSourceFactory constructor(
    private val dataSource: MainRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {

    val liveData = MutableLiveData<PopularMoviePageDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = PopularMoviePageDataSource(dataSource, scope)
        liveData.postValue(source)
        return source
    }
}