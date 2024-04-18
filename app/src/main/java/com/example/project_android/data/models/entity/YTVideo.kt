package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class YTVideo(
    @SerializedName("items")
    val items: List<YoutubeItem>
)

@Parcelize
data class YoutubeItem(
    @SerializedName("snippet")
    val snippet: Snippet
) : Parcelable

@Parcelize
data class Snippet(
    @SerializedName("description")
    val description: String
) : Parcelable
