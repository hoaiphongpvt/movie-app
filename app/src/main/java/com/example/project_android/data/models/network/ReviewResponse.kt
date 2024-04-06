package com.example.project_android.data.models.network

import android.os.Parcelable
import com.example.project_android.data.models.entity.Review
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewResponse (

    @SerializedName("results")
    val reviews: List<Review>,

    @SerializedName("total_results")
    val totalReviews : Int
): Parcelable