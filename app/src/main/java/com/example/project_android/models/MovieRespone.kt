package com.example.project_android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieRespone (
    @SerializedName("results")
    val movie : List<Movie>
) : Parcelable {
    constructor() : this(mutableListOf())
}