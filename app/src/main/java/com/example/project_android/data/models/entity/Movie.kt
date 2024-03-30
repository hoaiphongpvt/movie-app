package com.example.project_android.data.models.entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Objects

@Parcelize
data class Movie (
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("vote_count")
    val voteCount: Int = 0,

    @SerializedName("vote_average")
    val voteAverage: Float = 0f,

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language>? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null

) : Parcelable