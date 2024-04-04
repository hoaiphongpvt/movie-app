package com.example.project_android.data.services

import com.example.project_android.data.models.entity.SessionIdRequest
import com.example.project_android.data.models.network.BaseResponse
import com.example.project_android.data.models.network.SessionResponse
import com.example.project_android.data.models.network.SuccessResponse
import com.example.project_android.data.models.network.TokenResponse
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenApiInterface {

    //Check API Key
    @GET("${TheMovieDatabaseAPI.API_VERSION}/authentication")
    fun checkAPIKey(@Query("api_key") apiKey: String ) : Call<BaseResponse>

    //Get Request Token
    @GET("${TheMovieDatabaseAPI.API_VERSION}/authentication/token/new?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getRequestToken() : Call<TokenResponse>

    //Login
    @FormUrlEncoded
    @POST("${TheMovieDatabaseAPI.API_VERSION}/authentication/token/validate_with_login?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun loginWithRequestToken(@Field("username") username: String, @Field("password") password: String, @Field("request_token") requestToken: String) : Call<TokenResponse>

    //Create Session
    @FormUrlEncoded
    @POST("${TheMovieDatabaseAPI.API_VERSION}/authentication/session/new?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun createSession(@Field("request_token") requestToken: String) : Call<SessionResponse>

    //Delete Session
    @HTTP(method = "DELETE", path = "${TheMovieDatabaseAPI.API_VERSION}/authentication/session?api_key=${TheMovieDatabaseAPI.API_KEY}", hasBody = true)
    fun deleteSession(@Body sessionIdRequest: SessionIdRequest) : Call<SuccessResponse>
}

