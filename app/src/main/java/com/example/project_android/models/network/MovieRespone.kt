package com.example.project_android.models.network

import android.os.Parcelable
import com.example.project_android.models.entity.Movie
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieRespone (
    @SerializedName("results")
    val movie : List<Movie>
) : Parcelable {
    constructor() : this(mutableListOf())
}