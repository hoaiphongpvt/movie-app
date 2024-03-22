package com.example.project_android.services

import com.example.project_android.models.MovieRespone
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {
    @GET("/3//movie/popular?api_key=34ebeebee5dd3b001abf3ba575de0218")
    fun getMovieList() :Call<MovieRespone>

}