package com.example.project_android.services

import com.example.project_android.models.network.MovieRespone
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {
    //GET Popular Movies
    @GET("/3/movie/popular?api_key=34ebeebee5dd3b001abf3ba575de0218")
    fun getPopularMovieList() :Call<MovieRespone>

    //GET Upcoming Movies
    @GET("/3/movie/upcoming?api_key=34ebeebee5dd3b001abf3ba575de0218")
    fun getUpcomingMovieList() :Call<MovieRespone>

    //GET Top Rated Movies
    @GET("/3/movie/top_rated?api_key=34ebeebee5dd3b001abf3ba575de0218")
    fun getTopRatedMovieList() :Call<MovieRespone>
}