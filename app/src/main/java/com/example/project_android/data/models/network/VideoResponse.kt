package com.example.project_android.data.models.network

import android.os.Parcelable
import com.example.project_android.data.models.entity.Video
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoResponse(
    @SerializedName("results")
     var results: List<Video>
): Parcelable {
    constructor() : this(mutableListOf())
}
