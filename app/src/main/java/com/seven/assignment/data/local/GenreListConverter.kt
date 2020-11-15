package com.seven.assignment.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.seven.assignment.data.models.moviedetail.Genre
import java.util.*

class GenreListConverter {
    @TypeConverter
    fun fromGenreList(value: List<Genre>?): String? {
        if (value == null)
            return null
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGenreList(value: String?): List<Genre>? {
        if (value == null)
            return Collections.emptyList()
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(value, type)
    }
}