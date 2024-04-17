package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("key")
    var key: String? = null,

    @SerializedName("name")
    var name: String? = null,
) : Parcelable