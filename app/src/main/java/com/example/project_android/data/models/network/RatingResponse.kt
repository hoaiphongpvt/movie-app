package com.example.project_android.data.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingResponse(
    @SerializedName("status_code")
    val status_code: Int,

    @SerializedName("status_message")
    val status_message: String,

) : Parcelable
