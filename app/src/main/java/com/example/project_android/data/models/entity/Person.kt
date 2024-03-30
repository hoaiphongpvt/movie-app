package com.example.project_android.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("profile_path")
    var profilePath: String? = null,

    @SerializedName("known_for_department")
    var knownForDepartment: String? = null,

    @SerializedName("biography")
    var biography: String? = null
) : Parcelable