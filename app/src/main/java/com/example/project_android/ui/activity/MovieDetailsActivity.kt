package com.example.project_android.ui.activity


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Cast
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.models.entity.Video
import com.example.project_android.data.models.network.CastResponse
import com.example.project_android.data.models.network.VideoResponse
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import com.example.project_android.data.services.MovieApiInterface
import com.example.project_android.data.services.ApiServices
import com.example.project_android.ui.adapters.CastAdapter
import com.example.project_android.ui.adapters.VideoAdapter
import com.example.project_android.utils.convertDateFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    private val BASE_IMG = TheMovieDatabaseAPI.BASE_IMG
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val titlePage : TextView = findViewById(R.id.titlePage)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val movieBackground : ImageView = findViewById(R.id.movie_background)
        val movieTitle : TextView = findViewById(R.id.movieTitle)
        val genreText : TextView = findViewById(R.id.genresText)
        val rating : RatingBar = findViewById(R.id.ratingBar)
        val numOfVotes :  TextView = findViewById(R.id.numOfVotes)
        val episodeText : TextView = findViewById(R.id.episodeText)
        val seasonText : TextView = findViewById(R.id.seasonText)
        val movieLanguage : TextView = findViewById(R.id.movieLanguage)
        val overviewText : TextView = findViewById(R.id.overviewText)
        val castRecyclerView :  RecyclerView = findViewById(R.id.castRecyclerView)
        val videoRecyclerView: RecyclerView = findViewById(R.id.videosRecyclerView)

        val movie = intent.getParcelableExtra<Movie>("movie")

        if (movie != null) {
            getMovieDetailsData(movie.id.toString()) { movieDetails ->
                if (movieDetails != null) {
                    titlePage.text = movieDetails.title
                    movieTitle.text = movieDetails.title
                    genreText.text = movieDetails.genres?.map { it.name }?.joinToString(" / ")
                    Glide.with(movieBackground).load(BASE_IMG + movieDetails.backdropPath).into(movieBackground)
                    rating.rating = movieDetails.voteAverage / 2
                    numOfVotes.text = movieDetails.voteCount.toString() + " votes"
                    episodeText.text = movieDetails.releaseDate?.convertDateFormat()
                    seasonText.text = movieDetails.runtime.toString() + " min"
                    movieLanguage.text =
                        movieDetails.spokenLanguages?.joinToString(" / ") { it.englishName }
                    overviewText.text = movieDetails.overview

                    backButton.setOnClickListener {
                        onBackPressed()
                    }
                }
            }

            getListCastsData(movie.id.toString()) { casts: List<Cast> ->
                setupCastAdapter(castRecyclerView, casts)
            }

            getListVideosData(movie.id.toString()) { videos: List<Video> ->
                setupVideoAdapter(videoRecyclerView, videos)
            }
        }
    }
    private fun setupCastAdapter(recyclerView: RecyclerView, casts: List<Cast>) {
        recyclerView.adapter = CastAdapter(casts)
    }

    private fun setupVideoAdapter(recyclerView: RecyclerView, videos: List<Video>) {
        recyclerView.adapter = VideoAdapter(videos)
    }


    private fun getMovieDetailsData(movieID: String, callback: (Movie?) -> Unit) {
        val apiService = ApiServices.getInstance().create(MovieApiInterface::class.java)
        val call: Call<Movie> = apiService.getMovieDetails(movieID)

        call.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    callback(movieResponse)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback(null)
            }
        })
    }

    private fun getListCastsData(movieID: String, callback: (List<Cast>) -> Unit) {
        val apiService = ApiServices.getInstance().create(MovieApiInterface::class.java)
        val call: Call<CastResponse> = apiService.getListCasts(movieID)

        call.enqueue(object: Callback<CastResponse> {
            override fun onResponse(call: Call<CastResponse>, response: Response<CastResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.cast ?: emptyList())
                }
            }

            override fun onFailure(call: Call<CastResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getListVideosData(movieID: String, callback: (List<Video>) -> Unit) {
        val apiService = ApiServices.getInstance().create(MovieApiInterface::class.java)
        val call: Call<VideoResponse> = apiService.getListVideos(movieID)

        call.enqueue(object: Callback<VideoResponse> {
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.video ?: emptyList())
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

}