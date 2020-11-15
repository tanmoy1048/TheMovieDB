package com.seven.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seven.assignment.data.models.moviedetail.MovieDetail
import com.seven.assignment.data.models.movielist.Movie


@Database(
    entities = [Movie::class, MovieDetail::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(MovieShelfConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}