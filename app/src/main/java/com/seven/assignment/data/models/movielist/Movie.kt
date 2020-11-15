package com.seven.assignment.data.models.movielist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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
    @SerializedName("shelf")
    var shelf: MovieShelf? = null

    @Ignore
    @SerializedName("adult")
    val adult: Boolean? = null

    @Ignore
    @SerializedName("overview")
    val overview: String? = null

    @Ignore
    @SerializedName("release_date")
    val releaseDate: String? = null

    @Ignore
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null

    @Ignore
    @SerializedName("popularity")
    val popularity: Double? = null

    @Ignore
    @SerializedName("vote_count")
    val voteCount: Int? = null

    @Ignore
    @SerializedName("video")
    val video: Boolean? = null

    @Ignore
    @SerializedName("vote_average")
    val voteAverage: Double? = null

    @Ignore
    @SerializedName("original_language")
    val originalLanguage: String? = null
}