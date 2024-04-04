package com.example.project_android.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.network.SessionResponse
import com.example.project_android.data.models.network.TokenResponse
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.AuthenApiInterface
import com.example.project_android.ui.activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel(){

    val apiService = ApiServices.getInstance().create(AuthenApiInterface::class.java)
    fun login(username: String, password: String, callback: (Boolean, String) -> Unit) {
        apiService.getRequestToken().enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {

                if (response.isSuccessful) {
                    val results = response.body()
                    if (results?.success == true) {
                        loginWithToken(username, password, results.request_token) { tokenResponse ->
                            if (tokenResponse?.success == true) {
                                createSession(tokenResponse.request_token) { sessionResponse ->
                                    if (sessionResponse?.success == true) {
                                        callback(true, sessionResponse.session_id)
                                    } else {
                                        callback(false, "Login fail! Please try again!")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    callback(false, "Failed to get request token.")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                callback(false, "Failed to connect to server.")
            }
        })
    }

    fun loginWithToken(username: String, password: String, requestToken: String, callback: (TokenResponse?) -> Unit) {
        val call : Call<TokenResponse> = apiService.loginWithRequestToken(username, password, requestToken)

        call.enqueue(object: Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()
                    callback(results)
                } else {
                    Toast.makeText(context, "Username or password is not correct!", Toast.LENGTH_LONG).show()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to connect to server.", Toast.LENGTH_LONG).show()
                callback(null)
            }
        })
    }

    fun createSession(requestToken: String, callback: (SessionResponse?) -> Unit) {
        val call : Call<SessionResponse> = apiService.createSession(requestToken)

        call.enqueue(object : Callback<SessionResponse> {
            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()
                    callback(results)
                } else {
                    Toast.makeText(context, "Log in fail.", Toast.LENGTH_LONG).show()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to connect to server.", Toast.LENGTH_LONG).show()
                callback(null)
            }
        })
    }
}
