package com.example.project_android.utils

import android.content.Context

fun getAPIKey (context: Context) : String? {
    val sharedPrefs = context.getSharedPreferences("API_KEY", Context.MODE_PRIVATE)
    val apiKey = sharedPrefs.getString("API_KEY", null)

    return apiKey
}