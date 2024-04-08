package com.example.project_android.data.services

import com.example.project_android.data.models.entity.User
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiInterface {
    //GET Account Details
    @GET("${TheMovieDatabaseAPI.API_VERSION}/account/{account_id}?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getAccountDetails(@Path("account_id") accountID : Int, @Query("session_id") sessionID : String) : Call<User>
}