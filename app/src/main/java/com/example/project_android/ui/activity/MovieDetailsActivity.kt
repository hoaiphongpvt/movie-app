package com.example.project_android.ui.activity


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Cast
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.models.entity.Review
import com.example.project_android.data.models.entity.Video
import com.example.project_android.data.remote.TheMovieDatabaseAPI.BASE_IMG
import com.example.project_android.ui.adapters.CastAdapter
import com.example.project_android.ui.adapters.MovieAdapter
import com.example.project_android.ui.adapters.ReviewAdapter
import com.example.project_android.ui.adapters.VideoAdapter
import com.example.project_android.utils.convertDateFormat
import com.example.project_android.viewmodel.MovieDetailsViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var titlePage : TextView
    private lateinit var backButton : ImageButton
    private lateinit var movieBackground : ImageView
    private lateinit var movieTitle : TextView
    private lateinit var genreText : TextView
    private lateinit var rating : RatingBar
    private lateinit var numOfVotes : TextView
    private lateinit var episodeText: TextView
    private lateinit var seasonText: TextView
    private lateinit var movieLanguage: TextView
    private lateinit var overviewText: TextView
    private lateinit var totalReviews: TextView
    private lateinit var castRecyclerView: RecyclerView
    private lateinit var videoRecyclerView: RecyclerView
    private lateinit var recommendRecyclerView: RecyclerView
    private lateinit var reviewRecyclerView: RecyclerView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)

        titlePage = findViewById(R.id.titlePage)
        backButton = findViewById(R.id.backButton)
        movieBackground = findViewById(R.id.movie_background)
        movieTitle = findViewById(R.id.movieTitle)
        genreText = findViewById(R.id.genresText)
        rating = findViewById(R.id.ratingBar)
        numOfVotes = findViewById(R.id.numOfVotes)
        episodeText = findViewById(R.id.episodeText)
        seasonText = findViewById(R.id.seasonText)
        movieLanguage = findViewById(R.id.movieLanguage)
        overviewText = findViewById(R.id.overviewText)
        totalReviews = findViewById(R.id.totalReviews)
        castRecyclerView = findViewById(R.id.castRecyclerView)
        videoRecyclerView = findViewById(R.id.videosRecyclerView)
        recommendRecyclerView = findViewById(R.id.recommendRecyclerview)
        reviewRecyclerView = findViewById(R.id.reviewRecyclerview)

        val movieID = intent.getStringExtra("movieID")

        if (movieID != null) {
            movieDetailsViewModel.getMovieDetailsData(movieID) { movieDetails ->
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

            movieDetailsViewModel.getListCastsData(movieID) { casts: List<Cast> ->
                setupCastAdapter(castRecyclerView, casts)
            }

            movieDetailsViewModel.getListVideosData(movieID) { videos: List<Video> ->
                setupVideoAdapter(videoRecyclerView, videos)
            }

            movieDetailsViewModel.getListRecommendMovies(movieID) {movies : List<Movie> ->
                setupMovieAdapter(recommendRecyclerView, movies)
            }

            movieDetailsViewModel.getReviews(movieID) { reviews: List<Review>, total: Int ->
                totalReviews.text = "(${total})"
                setupReviewAdapter(reviewRecyclerView, reviews)
            }
        }
    }
    private fun setupCastAdapter(recyclerView: RecyclerView, casts: List<Cast>) {
        recyclerView.adapter = CastAdapter(casts) { cast ->
            val intent = Intent(this, CastDetailsActivity::class.java)
            intent.putExtra("castID", cast.id.toString())
            startActivity(intent)
        }
    }

    private fun setupMovieAdapter(recyclerView: RecyclerView, movies: List<Movie>) {
        recyclerView.adapter = MovieAdapter(movies) { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("movieID", movie.id.toString())
            startActivity(intent)
        }
    }

    private fun setupVideoAdapter(recyclerView: RecyclerView, videos: List<Video>) {
        recyclerView.adapter = VideoAdapter(videos) { video ->
            val intent = Intent(this, VideoDetailsActivity::class.java)
            intent.putExtra("videoName", video.name.toString())
            intent.putExtra("videoKey", video.key.toString())
            startActivity(intent)
        }
    }

    private fun setupReviewAdapter(recyclerView: RecyclerView, reviews: List<Review>) {
        recyclerView.adapter = ReviewAdapter(reviews)
    }
}