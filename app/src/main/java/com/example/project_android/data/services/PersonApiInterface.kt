package com.example.project_android.data.services

import com.example.project_android.data.models.entity.Person
import com.example.project_android.data.models.network.PersonCreditResponse
import com.example.project_android.data.models.network.PersonImageResponse
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApiInterface {

    //Get Person Details
    @GET("${TheMovieDatabaseAPI.API_VERSION}/person/{person_id}?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getPersonDetails(@Path("person_id") id: String) : Call<Person>

    //Get List Images Person
    @GET("${TheMovieDatabaseAPI.API_VERSION}/person/{person_id}/images?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getListImagesPerson(@Path("person_id") id: String) : Call<PersonImageResponse>

    //GET Combined Credits
    @GET("${TheMovieDatabaseAPI.API_VERSION}/person/{person_id}/combined_credits?api_key=${TheMovieDatabaseAPI.API_KEY}")
    fun getCombinedCredits(@Path("person_id") id: String) : Call<PersonCreditResponse>
}