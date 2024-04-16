package com.example.project_android.data.services

import com.example.project_android.data.models.entity.FavoriteRequest
import com.example.project_android.data.models.entity.User
import com.example.project_android.data.models.network.BaseResponse
import com.example.project_android.data.models.network.MovieResponse
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiInterface {
    //GET Account Details
    @GET("${TheMovieDatabaseAPI.API_VERSION}/account/{account_id}?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getAccountDetails(@Path("account_id") accountID : Int, @Query("session_id") sessionID : String) : Call<User>

    //Get favorite movies
    @GET("${TheMovieDatabaseAPI.API_VERSION}/account/{account_id}/favorite/movies?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getFavoriteMovie(@Path("account_id") accountId : Int, @Query("session_id") sessionId : String) : Call<MovieResponse>

   //Add to favorite movie
    @POST("${TheMovieDatabaseAPI.API_VERSION}/account/{account_id}/favorite?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun addToFavorite(@Path("account_id") accountId : Int, @Query("session_id") sessionID : String,@Body favoriteRequest : FavoriteRequest ) : Call<BaseResponse>

    //Delete favorite movie
    @POST("${TheMovieDatabaseAPI.API_VERSION}/account/{account_id}/favorite?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun deleteToFavorite(@Path("account_id") accountId : Int, @Query("session_id") sessionID : String, @Body favoriteRequest: FavoriteRequest) : Call<BaseResponse>
}