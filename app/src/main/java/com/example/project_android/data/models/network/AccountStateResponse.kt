package com.example.project_android.data.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class AccountStateResponse(
    @SerializedName("favorite")
    val favorite: Boolean,

    @SerializedName("id")
    val id: Int,

    @SerializedName("rated")
    val rated: @RawValue Any? = false,

    ) : Parcelable