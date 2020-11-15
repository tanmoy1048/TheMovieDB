package com.seven.assignment.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.seven.assignment.data.models.moviedetail.Genre
import com.seven.assignment.data.paging.MovieShelf
import java.util.*

class MovieShelfConverter {
    @TypeConverter
    fun fromMovieShelf(value: MovieShelf?): String? {
        if (value == null)
            return null
        val gson = Gson()
        return gson.toJson(value, MovieShelf::class.java)
    }

    @TypeConverter
    fun toMovieShelf(value: String?): MovieShelf? {
        if (value == null)
            return null
        val gson = Gson()
        return gson.fromJson(value, MovieShelf::class.java)
    }
}