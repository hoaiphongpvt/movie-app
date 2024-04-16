package com.example.project_android.data.models.network

import android.os.Parcelable
import com.example.project_android.data.models.entity.Rated
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountStateResponse(
    @SerializedName("favorite")
    val favorite: Boolean,

    @SerializedName("id")
    val id: Int,

//    @SerializedName("rated")
//    val rated: Rated,
//
//    @SerializedName("watchlist")
//    val watchlist: Boolean

) : Parcelable