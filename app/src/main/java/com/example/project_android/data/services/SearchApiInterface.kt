package com.example.project_android.data.services

import com.example.project_android.data.models.network.MovieResponse
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiInterface {

    //Search keyword
    @GET("${TheMovieDatabaseAPI.API_VERSION}/search/movie?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun searchMovie(@Query("query") query: String) : Call<MovieResponse>
}