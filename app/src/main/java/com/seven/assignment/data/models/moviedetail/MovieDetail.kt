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

    @Ignore
    @SerializedName("adult")
    var adult: Boolean? = null

    @Ignore
    @SerializedName("belongs_to_collection")
    var belongsToCollection: String? = null

    @Ignore
    @SerializedName("budget")
    var budget: Int? = null

    @Ignore
    @SerializedName("homepage")
    var homepage: String? = null

    @Ignore
    @SerializedName("imdb_id")
    var imdbId: String? = null

    @Ignore
    @SerializedName("original_language")
    var originalLanguage: String? = null

    @Ignore
    @SerializedName("overview")
    var overview: String? = null

    @Ignore
    @SerializedName("popularity")
    var popularity: Double? = null

    @Ignore
    @SerializedName("poster_path")
    var posterPath: String? = null

    @Ignore
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany>? = null

    @Ignore
    @SerializedName("production_countries")
    var productionCountries: List<Production_countries>? = null

    @Ignore
    @SerializedName("release_date")
    var releaseDate: String? = null

    @Ignore
    @SerializedName("revenue")
    var revenue: Int? = null

    @Ignore
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage>? = null

    @Ignore
    @SerializedName("status")
    var status: String? = null

    @Ignore
    @SerializedName("tagline")
    var tagline: String? = null

    @Ignore
    @SerializedName("video")
    var video: Boolean? = null

    @Ignore
    @SerializedName("vote_count")
    var voteCount: Int? = null
}