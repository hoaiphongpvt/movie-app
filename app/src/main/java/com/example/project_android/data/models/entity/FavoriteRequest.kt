package com.example.project_android.data.models.entity

import com.google.gson.annotations.SerializedName

data class FavoriteRequest(
    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("media_id")
    val mediaId: String,

    @SerializedName("favorite")
    val favorite: Boolean,
)
