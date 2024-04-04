package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Avatar(
    @SerializedName("gravatar")
    val gravatar: Gravatar,

    @SerializedName("tmdb")
    val tmdb: Tmdb
) : Parcelable