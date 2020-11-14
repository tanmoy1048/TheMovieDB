package com.seven.assignment.data.remote

import com.seven.assignment.data.models.PaginatedResponse
import com.seven.assignment.data.models.moviedetail.MovieDetail
import com.seven.assignment.data.models.movielist.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/popular/")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): Response<PaginatedResponse<Movie>>

    @GET("/3/movie/now_playing/")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): Response<PaginatedResponse<Movie>>

    @GET("/3/movie/top_rated/")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): Response<PaginatedResponse<Movie>>

    @GET("/3/movie/upcoming/")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): Response<PaginatedResponse<Movie>>

    @GET("/3/movie/{movie_id}/")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): Response<MovieDetail>
}