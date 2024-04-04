package com.example.project_android.viewmodel

import android.util.Log
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.AuthenApiInterface
import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.entity.SessionIdRequest
import com.example.project_android.data.models.entity.User
import com.example.project_android.data.models.network.SuccessResponse
import com.example.project_android.data.services.UserApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val apiAuthService = ApiServices.getInstance().create(AuthenApiInterface::class.java)
    private val apiUserService = ApiServices.getInstance().create(UserApiInterface::class.java)

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

    fun getUserDetails(accountID : Int, sessionID: String, callback: (User?, String?) -> Unit) {
        apiUserService.getAccountDetails(accountID, sessionID).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    callback(user, "success")
                } else {
                    callback(null, "fail")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null, "fail to call api")
            }
        })
    }
}