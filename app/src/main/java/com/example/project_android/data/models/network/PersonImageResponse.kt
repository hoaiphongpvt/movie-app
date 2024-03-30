package com.example.project_android.data.models.network

import android.os.Parcelable
import com.example.project_android.data.models.entity.Image
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonImageResponse(
    @SerializedName("profiles")
    var results: List<Image>
) : Parcelable {
    constructor() : this(mutableListOf())
}
