package com.example.project_android.data.services

import com.example.project_android.data.models.entity.YTVideo
import com.example.project_android.data.remote.YoutubeDataAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoApiInterface {
    @GET("videos?key=${YoutubeDataAPI.API_KEY}&part=snippet")
    fun getVideoData(@Query("id") id : String): Call<YTVideo>
}