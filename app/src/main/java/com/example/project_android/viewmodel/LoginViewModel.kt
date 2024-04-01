package com.example.project_android.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.entity.BaseResponse
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.AuthenApiInterface
import com.example.project_android.ui.activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel(){

    fun login(apiKey: String) {

        val apiService = ApiServices.getInstance().create(AuthenApiInterface::class.java)
        val call : Call<BaseResponse> = apiService.checkAPIKey(apiKey)

        call.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()
                    if (results != null) {
                        if (results.success) {
                            Toast.makeText(context, results.status_message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Please check your API Key again!", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "API Key is not correct!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Toast.makeText(context, "An error occurred: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
