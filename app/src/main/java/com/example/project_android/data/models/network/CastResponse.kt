package com.example.project_android.data.models.network

import android.os.Parcelable
import com.example.project_android.data.models.entity.Cast
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CastResponse (
    @SerializedName("cast")
    val cast : List<Cast>
) : Parcelable {
    constructor() : this(mutableListOf())
}
