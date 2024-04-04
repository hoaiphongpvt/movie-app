package com.example.project_android.viewmodel

import android.util.Log
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.AuthenApiInterface
import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.entity.SessionIdRequest
import com.example.project_android.data.models.network.SuccessResponse
import com.example.project_android.data.services.UserApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    val apiAuthService = ApiServices.getInstance().create(AuthenApiInterface::class.java)
    val apiUserService = ApiServices.getInstance().create(UserApiInterface::class.java)

    fun logout(sessionID: String, callback: (Boolean, String?) -> Unit) {
        val sessionIdRequest = SessionIdRequest(sessionID)
        apiAuthService.deleteSession(sessionIdRequest).enqueue(object : Callback<SuccessResponse> {
            override fun onResponse(call: Call<SuccessResponse>, response: Response<SuccessResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.success == true) {
                        callback(true, null)
                    }
                } else {
                    val errorMessage = "Error: ${response.code()} ${response.message()}"
                    Log.d("Error", errorMessage)
                    callback(false, errorMessage)
                }
            }

            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                val errorMessage = "Failure: ${t.message}"
                Log.d("Failure", errorMessage)
                callback(false, errorMessage)
            }
        })
    }
}