package com.example.project_android.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.entity.Cast
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.models.entity.Video
import com.example.project_android.data.models.network.CastResponse
import com.example.project_android.data.models.network.VideoResponse
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.MovieApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel : ViewModel() {

     fun getMovieDetailsData(movieID: String, callback: (Movie?) -> Unit) {
        val apiService = ApiServices.getInstance().create(MovieApiInterface::class.java)
        val call: Call<Movie> = apiService.getMovieDetails(movieID)

        call.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    callback(movieResponse)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback(null)
            }
        })
    }

     fun getListCastsData(movieID: String, callback: (List<Cast>) -> Unit) {
        val apiService = ApiServices.getInstance().create(MovieApiInterface::class.java)
        val call: Call<CastResponse> = apiService.getListCasts(movieID)

        call.enqueue(object: Callback<CastResponse> {
            override fun onResponse(call: Call<CastResponse>, response: Response<CastResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.cast ?: emptyList())
                }
            }

            override fun onFailure(call: Call<CastResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

     fun getListVideosData(movieID: String, callback: (List<Video>) -> Unit) {
        val apiService = ApiServices.getInstance().create(MovieApiInterface::class.java)
        val call: Call<VideoResponse> = apiService.getListVideos(movieID)

        call.enqueue(object: Callback<VideoResponse> {
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.video ?: emptyList())
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

}