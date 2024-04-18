package com.example.project_android.data.models.entity

import com.google.gson.annotations.SerializedName

data class RatingRequest(
    @SerializedName("value")
    val value: Float
)
