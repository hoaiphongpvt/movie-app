package com.example.project_android.data.services

import com.example.project_android.data.models.network.MovieRespone
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {
    //GET Popular Movies
    @GET("/${TheMovieDatabaseAPI.API_VERSION}/movie/popular?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getPopularMovieList() :Call<MovieRespone>

    //GET Upcoming Movies
    @GET("/${TheMovieDatabaseAPI.API_VERSION}/movie/upcoming?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getUpcomingMovieList() :Call<MovieRespone>

    //GET Top Rated Movies
    @GET("/${TheMovieDatabaseAPI.API_VERSION}/movie/top_rated?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getTopRatedMovieList() :Call<MovieRespone>

    //Get Now Playing Movies
    @GET("${TheMovieDatabaseAPI.API_VERSION}/movie/now_playing?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getNowPlayingMovieList() :Call<MovieRespone>

    //Get Trending Movies
    @GET("${TheMovieDatabaseAPI.API_VERSION}/trending/movie/day?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getTrendingMovieList() :Call<MovieRespone>
}