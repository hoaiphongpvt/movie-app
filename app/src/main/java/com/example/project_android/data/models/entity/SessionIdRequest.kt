package com.example.project_android.data.models.entity

import com.google.gson.annotations.SerializedName

data class SessionIdRequest(
    @SerializedName("session_id") val sessionId: String
)
