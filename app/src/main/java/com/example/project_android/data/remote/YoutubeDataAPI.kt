package com.example.project_android.data.remote
object TheMovieDatabaseAPI {
    const val API_VERSION: Int = 3
    const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w185"
    const val BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780"
    const val BASE_IMG = "https://image.tmdb.org/t/p/w500/"
    const val BASE_PROFILE_URL = "https://image.tmdb.org/t/p/w185"
    const val BASE_YT_IMG_URL = "https://img.youtube.com/vi/"
    const val BASE_YT_WATCH_URL = "https://www.youtube.com/watch?v="
    const val API_KEY : String = "34ebeebee5dd3b001abf3ba575de0218"
    const val YOUTUBE_API_KEY : String = "AIzaSyAUg0kqixd5ataD4fnxcsxeBFQmrBlsOiM"
    const val BASE_API_URL = "https://api.themoviedb.org/"

    fun getPosterUrl(path: String) = BASE_POSTER_URL + path
    fun getBackdropUrl(path: String) = BASE_BACKDROP_URL + path
    fun getProfileUrl(path: String) = BASE_PROFILE_URL + path
    fun getYoutubeImageUrl(youtubeId: String) = "$BASE_YT_IMG_URL$youtubeId/hqdefault.jpg"
    fun getYoutubeWatchUrl(youtubeId: String) = "$BASE_YT_WATCH_URL$youtubeId"/**/
    fun getRuntimeDateFormat() = ("yyyy-MM-dd")

}

