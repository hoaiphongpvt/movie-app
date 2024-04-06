package com.example.project_android.data.services

import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.models.network.CastResponse
import com.example.project_android.data.models.network.MovieResponse
import com.example.project_android.data.models.network.VideoResponse
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiInterface {

    //GET Popular Movies
    @GET("/${TheMovieDatabaseAPI.API_VERSION}/movie/popular?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getPopularMovieList() :Call<MovieResponse>

    //GET Upcoming Movies
    @GET("${TheMovieDatabaseAPI.API_VERSION}/movie/upcoming?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getUpcomingMovieList() :Call<MovieResponse>

    //GET Top Rated Movies
    @GET("${TheMovieDatabaseAPI.API_VERSION}/movie/top_rated?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getTopRatedMovieList() :Call<MovieResponse>

    //Get Now Playing Movies
    @GET("${TheMovieDatabaseAPI.API_VERSION}/movie/now_playing?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getNowPlayingMovieList() :Call<MovieResponse>

    //Get Trending Movies
    @GET("${TheMovieDatabaseAPI.API_VERSION}/trending/movie/day?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getTrendingMovieList() :Call<MovieResponse>

    //Get Movie Details
    @GET("${TheMovieDatabaseAPI.API_VERSION}/movie/{movie_id}?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getMovieDetails(@Path("movie_id") id: String): Call<Movie>

    //Get List Casts
    @GET("${TheMovieDatabaseAPI.API_VERSION}/movie/{movie_id}/credits?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getListCasts(@Path("movie_id") id: String) : Call<CastResponse>

    //Get List Video
    @GET("${TheMovieDatabaseAPI.API_VERSION}/movie/{movie_id}/videos?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getListVideos(@Path("movie_id") id: String) : Call<VideoResponse>

    //Recommend movies
    @GET("${TheMovieDatabaseAPI.API_VERSION}/movie/{movie_id}/recommendations?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getRecommendMovie(@Path("movie_id") id : String) : Call<MovieResponse>
}