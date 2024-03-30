package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image (
    @SerializedName("file_path")
    var filePath: String? = null,
) : Parcelable