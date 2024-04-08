package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tmdb(
    @SerializedName("avatar_path")
    val avatar_path: String
) : Parcelable