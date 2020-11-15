package com.seven.assignment.data.models.moviedetail

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.seven.assignment.data.local.GenreListConverter

@Entity(tableName = "MovieDetail")
@TypeConverters(GenreListConverter::class)
class MovieDetail {
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    var genres: List<Genre>? = null

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int? = null

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    var originalTitle: String? = null

    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    var runtime: Int? = null

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String? = null

    @ColumnInfo(name = "voteAverage")
    @SerializedName("vote_average")
    var voteAverage: Double? = null
}