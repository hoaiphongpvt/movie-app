package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("avatar")
    val avatar: Avatar,

    @SerializedName("id")
    val id: Int,

    @SerializedName("include_adult")
    val include_adult: Boolean,

    @SerializedName("iso_3166_1")
    val iso_3166_1: String,

    @SerializedName("iso_639_1")
    val iso_639_1: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String
) : Parcelable