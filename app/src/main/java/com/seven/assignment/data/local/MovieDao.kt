package com.seven.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seven.assignment.data.models.moviedetail.MovieDetail
import com.seven.assignment.data.models.movielist.Movie


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetail)

    @Query("SELECT * FROM MovieDetail WHERE id = :movieId")
    fun getMovieDetail(movieId: Int): LiveData<MovieDetail?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM Movie")
    fun getMovies(): DataSource.Factory<Int, Movie>
}