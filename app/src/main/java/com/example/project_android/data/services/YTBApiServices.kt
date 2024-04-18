package com.example.project_android.data.services

import com.example.project_android.data.remote.YoutubeDataAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YTBApiServices {
    companion object {
        private const val BASE_URL = YoutubeDataAPI.BASE_API_URL
        private var retrofit: Retrofit? = null

        fun getInstance() : Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}