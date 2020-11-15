package com.seven.assignment.data.models.movielist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.seven.assignment.data.paging.MovieShelf


@Entity(tableName = "Movie")
class Movie(
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path") val posterPath: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") val id: Int,
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title") val originalTitle: String,
    @ColumnInfo(name = "title")
    @SerializedName("title") val title: String,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path") val backdropPath: String?,
) {
    @ColumnInfo(name = "shelf")
    var shelf: MovieShelf? = null
}