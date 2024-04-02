package com.example.project_android.data.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class TokenResponse (
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("expires_at")
    val expires_at: String,

    @SerializedName("request_token")
    val request_token: String,
) : Parcelable