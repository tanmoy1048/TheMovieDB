package com.seven.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seven.assignment.data.models.moviedetail.MovieDetail
import com.seven.assignment.data.models.movielist.Movie


@Database(
    entities = [Movie::class, MovieDetail::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}