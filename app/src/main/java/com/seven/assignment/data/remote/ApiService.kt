package com.seven.assignment.data.remote

import com.seven.assignment.data.models.Movie
import com.seven.assignment.data.models.PaginatedResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/movie/popular/")
    suspend fun getPopularMovies(): Response<PaginatedResponse<Movie>>

    @GET("/movie/now_playing/")
    suspend fun getNowPlayingMovies(): Response<PaginatedResponse<Movie>>

    @GET("/movie/top_rated/")
    suspend fun getTopRatedMovies(): Response<PaginatedResponse<Movie>>

    @GET("/movie/upcoming/")
    suspend fun getUpcomingMovies(): Response<PaginatedResponse<Movie>>
}