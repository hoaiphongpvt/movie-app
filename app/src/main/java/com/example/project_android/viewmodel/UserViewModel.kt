package com.example.project_android.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.AuthenApiInterface
import androidx.lifecycle.ViewModel
import com.example.project_android.R
import com.example.project_android.data.models.entity.SessionIdRequest
import com.example.project_android.data.models.entity.User
import com.example.project_android.data.models.network.MovieResponse
import com.example.project_android.data.models.network.SuccessResponse
import com.example.project_android.data.services.UserApiInterface
import com.example.project_android.ui.activity.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val context: Context) : ViewModel() {

    private val apiAuthService = ApiServices.getInstance().create(AuthenApiInterface::class.java)
    private val apiUserService = ApiServices.getInstance().create(UserApiInterface::class.java)
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

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

    fun getFavoriteMovie(accountID: Int, sessionID: String, callback: (MovieResponse?) -> Unit) {
        apiUserService.getFavoriteMovie(accountID, sessionID).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
               if (response.isSuccessful) {
                   val favoriteMovies = response.body()
                   callback(favoriteMovies)
               }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                callback(null)
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

    fun getUserGoogle(): FirebaseUser? {
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(R.string.default_web_client_id.toString())
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

        val auth = Firebase.auth
        return auth.currentUser
    }

    fun logoutGoogle(callback: (Boolean) -> Unit) {
        mAuth.signOut()

        // Đăng xuất khỏi Google Sign-In Client
        mGoogleSignInClient.signOut().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true)
            } else {
                callback(false)
            }
        }
    }
}