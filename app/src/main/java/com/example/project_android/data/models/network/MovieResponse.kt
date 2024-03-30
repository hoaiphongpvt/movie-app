package com.example.project_android.data.models.network

import android.os.Parcelable
import com.example.project_android.data.models.entity.Movie
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse (
    @SerializedName("results")
    val movie : List<Movie>
) : Parcelable {
    constructor() : this(mutableListOf())
}