package com.example.project_android.models.entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Movie (
    @SerializedName("id")
    val id: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("poster_path")
    val poster_path: String?,

    @SerializedName("release_date")
    val release: String,


) : Parcelable {
    constructor() :this("", "", "", "")
}