package com.example.project_android.data.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class SessionResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("session_id")
    val session_id: String
) : Parcelable