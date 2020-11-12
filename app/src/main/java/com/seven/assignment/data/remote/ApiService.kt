package com.seven.assignment.data.remote

import com.seven.assignment.data.models.Movie
import com.seven.assignment.data.models.PaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/movie/popular/")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): Response<PaginatedResponse<Movie>>

    @GET("/movie/now_playing/")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): Response<PaginatedResponse<Movie>>

    @GET("/movie/top_rated/")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): Response<PaginatedResponse<Movie>>

    @GET("/movie/upcoming/")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): Response<PaginatedResponse<Movie>>
}