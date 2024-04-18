package com.example.project_android.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.entity.YTVideo
import com.example.project_android.data.services.VideoApiInterface
import com.example.project_android.data.services.YTBApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoViewModel: ViewModel() {
    private val apiService = YTBApiServices.getInstance().create(VideoApiInterface::class.java)
    fun getVideoData(videoID: String, callback: (String?) -> Unit) {
        val call: Call<YTVideo> = apiService.getVideoData(videoID)
        call.enqueue(object : Callback<YTVideo> {
            override fun onResponse(call: Call<YTVideo>, response: Response<YTVideo>) {
                if (response.isSuccessful) {
                    val youtubeResponse = response.body()
                    val description = youtubeResponse?.items?.firstOrNull()?.snippet?.description
                    callback(description)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<YTVideo>, t: Throwable) {
                // Handle failure case here
                callback(null)
            }
        })
    }
}
