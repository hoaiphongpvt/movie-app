package com.example.project_android.data.models.network

import android.os.Parcelable
import com.example.project_android.data.models.entity.Genre
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreResponse(
    @SerializedName("genres")
    val genre : List<Genre>
) : Parcelable {
    constructor() : this(mutableListOf())
}
