package com.example.project_android.data.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SuccessResponse (
    @SerializedName("success")
    val success: Boolean
) : Parcelable