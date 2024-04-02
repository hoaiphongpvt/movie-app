package com.example.project_android.data.models.network

import android.os.Parcelable
import com.example.project_android.data.models.entity.Credit
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonCreditResponse(
    @SerializedName("cast")
    val results: List<Credit>
) : Parcelable {
    constructor() : this(mutableListOf())
}