package com.example.project_android.data.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GuestSessionResponse (

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("expires_at")
    val expiresAt: String,

    @SerializedName("guest_session_id")
    val guestSessionId: String,

    ) : Parcelable