package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(
    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("iso_639_1")
    val iso_639_1: String,

    @SerializedName("name")
    val name: String

) : Parcelable
