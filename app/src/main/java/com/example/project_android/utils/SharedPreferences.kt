package com.example.project_android.utils

import android.content.Context

fun saveSessionId(context: Context, sessionId: String) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("sessionId", sessionId)
    editor.apply()
}

fun getSessionId(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("sessionId", null)
}

fun clearSession(context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove("sessionId")
    editor.apply()
}