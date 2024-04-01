package com.example.project_android.data.services

import com.example.project_android.data.models.entity.BaseResponse
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthenApiInterface {

    //Check API Key
    @GET("${ TheMovieDatabaseAPI.API_VERSION}/authentication")
    fun checkAPIKey(@Query("api_key") apiKey: String ) : Call<BaseResponse>
}