package com.example.project_android.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.models.network.MovieResponse
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.SearchApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val apiService = ApiServices.getInstance().create(SearchApiInterface::class.java)

    fun searchMovie(query : String, callback: (List<Movie>) -> Unit) {
        apiService.searchMovie(query).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.movie ?: emptyList())
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                callback(emptyList())
            }
        })
    }
}