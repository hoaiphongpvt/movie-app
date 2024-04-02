package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credit (
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("media_type")
    var mediaType: String? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("original_title", alternate = ["original_name"])
    var title: String? = null
) : Parcelable