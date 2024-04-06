package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class AuthorDetails(

    @SerializedName("avatar_path")
    val avatarPath: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("rating")
    val rating: Int? = null,

    @SerializedName("username")
    val username: String
) : Parcelable