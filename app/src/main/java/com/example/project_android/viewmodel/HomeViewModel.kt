package com.example.project_android.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.models.network.MovieResponse
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.MovieApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

     fun getMovieData(type: String, callback: (List<Movie>) -> Unit) {
        val apiService = ApiServices.getInstance().create(MovieApiInterface::class.java)
        val call: Call<MovieResponse> = when (type) {
            "popular" -> apiService.getPopularMovieList()
            "upcoming" -> apiService.getUpcomingMovieList()
            "topRated" -> apiService.getTopRatedMovieList()
            "trending" -> apiService.getTrendingMovieList()
            // Thêm các trường hợp khác nếu cần
            else -> apiService.getPopularMovieList() // Mặc định lấy dữ liệu phim phổ biến nếu không xác định được loại
        }
        call.enqueue(object : Callback<MovieResponse> {
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