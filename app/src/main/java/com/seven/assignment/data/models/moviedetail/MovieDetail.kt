package com.seven.assignment.data.models.moviedetail

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.seven.assignment.data.local.GenreListConverter

@Entity(tableName = "MovieDetail")
@TypeConverters(GenreListConverter::class)
class MovieDetail(
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "genres")
    @SerializedName("genres") val genres: List<Genre>,
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") val id: Int,
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title") val originalTitle: String,
    @ColumnInfo(name = "runtime")
    @SerializedName("runtime") val runtime: Int?,
    @ColumnInfo(name = "title")
    @SerializedName("title") val title: String,
    @ColumnInfo(name = "voteAverage")
    @SerializedName("vote_average") val voteAverage: Double,
) {
    @Ignore
    @SerializedName("adult")
    val adult: Boolean? = null

    @Ignore
    @SerializedName("belongs_to_collection")
    val belongsToCollection: String? = null

    @Ignore
    @SerializedName("budget")
    val budget: Int? = null

    @Ignore
    @SerializedName("homepage")
    val homepage: String? = null

    @Ignore
    @SerializedName("imdb_id")
    val imdbId: String? = null

    @Ignore
    @SerializedName("original_language")
    val originalLanguage: String? = null

    @Ignore
    @SerializedName("overview")
    val overview: String? = null

    @Ignore
    @SerializedName("popularity")
    val popularity: Double? = null

    @Ignore
    @SerializedName("poster_path")
    val posterPath: String? = null

    @Ignore
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>? = null

    @Ignore
    @SerializedName("production_countries")
    val productionCountries: List<Production_countries>? = null

    @Ignore
    @SerializedName("release_date")
    val releaseDate: String? = null

    @Ignore
    @SerializedName("revenue")
    val revenue: Int? = null

    @Ignore
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null

    @Ignore
    @SerializedName("status")
    val status: String? = null

    @Ignore
    @SerializedName("tagline")
    val tagline: String? = null

    @Ignore
    @SerializedName("video")
    val video: Boolean? = null

    @Ignore
    @SerializedName("vote_count")
    val voteCount: Int? = null
}