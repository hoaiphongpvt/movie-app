package com.example.project_android.data.models.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gravatar(
    val hash: String
) : Parcelable