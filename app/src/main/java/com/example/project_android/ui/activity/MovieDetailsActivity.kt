package com.example.project_android.ui.activity


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codemybrainsout.ratingdialog.RatingDialog
import com.example.loadinganimation.LoadingAnimation
import com.example.project_android.R
import com.example.project_android.data.models.entity.Cast
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.models.entity.Review
import com.example.project_android.data.models.entity.Video
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import com.example.project_android.data.remote.TheMovieDatabaseAPI.BASE_IMG
import com.example.project_android.ui.adapters.CastAdapter
import com.example.project_android.ui.adapters.MovieAdapter
import com.example.project_android.ui.adapters.ReviewAdapter
import com.example.project_android.ui.adapters.VideoAdapter
import com.example.project_android.utils.convertDateFormat
import com.example.project_android.viewmodel.MovieDetailsViewModel
import com.example.project_android.viewmodel.UserViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var userViewModel : UserViewModel
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
    private lateinit var btnFav : ToggleButton
    private lateinit var btnRate : ToggleButton
    private lateinit var overviewText: TextView
    private lateinit var totalReviews: TextView
    private lateinit var castRecyclerView: RecyclerView
    private lateinit var videoRecyclerView: RecyclerView
    private lateinit var recommendRecyclerView: RecyclerView
    private lateinit var reviewRecyclerView: RecyclerView
    private var sessionID: String? = null
    private lateinit var loadingAnim: LoadingAnimation
    private val accountID = TheMovieDatabaseAPI.ACCOUNT_ID
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        loadingAnim = findViewById(R.id.loadingAnim)
        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        userViewModel = UserViewModel(this)
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
        btnFav = findViewById(R.id.favButton)
        btnRate = findViewById(R.id.ratedButton)
        overviewText = findViewById(R.id.overviewText)
        totalReviews = findViewById(R.id.totalReviews)
        castRecyclerView = findViewById(R.id.castRecyclerView)
        videoRecyclerView = findViewById(R.id.videosRecyclerView)
        recommendRecyclerView = findViewById(R.id.recommendRecyclerview)
        reviewRecyclerView = findViewById(R.id.reviewRecyclerview)

        var completedRequests = 0
        val totalRequests = 6

        fun checkAllRequestsCompleted() {
            completedRequests++
            if (completedRequests == totalRequests) {
                loadingAnim.visibility = View.GONE
            }
        }

        val movieID = intent.getStringExtra("movieID")
        sessionID = intent.getStringExtra("sessionID")

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
                    checkAllRequestsCompleted()
                }
            }

            movieDetailsViewModel.getListCastsData(movieID) { casts: List<Cast> ->
                setupCastAdapter(castRecyclerView, casts)
                checkAllRequestsCompleted()
            }

            movieDetailsViewModel.getListVideosData(movieID) { videos: List<Video> ->
                setupVideoAdapter(videoRecyclerView, videos)
                checkAllRequestsCompleted()
            }

            movieDetailsViewModel.getListRecommendMovies(movieID) {movies : List<Movie> ->
                setupMovieAdapter(recommendRecyclerView, movies)
                checkAllRequestsCompleted()
            }

            movieDetailsViewModel.getReviews(movieID) { reviews: List<Review>, total: Int ->
                totalReviews.text = "(${total})"
                setupReviewAdapter(reviewRecyclerView, reviews)
                checkAllRequestsCompleted()

            }

            if (sessionID != null) {
                movieDetailsViewModel.checkAccountState(movieID, sessionID!!) { accountState, msg ->
                    if (accountState != null) {
                        if(accountState.favorite) {
                            btnFav.isChecked = true
                            btnFav.text = "Favorite"
                            btnFav.setBackgroundResource(R.color.starColor)
                        } else {
                            btnFav.isChecked = false
                            btnFav.text = "Add to favorite"
                            btnFav.setBackgroundResource(R.color.btnPrimary)
                        }

                        if (accountState.rated != false) {
                            btnRate.isChecked = true
                            btnRate.text = "Rated " + accountState.rated.toString().substringAfterLast("=").trim().dropLast(1).substringBefore(".") + " ⭐"
                            btnRate.setBackgroundResource(R.color.starColor)
                        } else {
                            btnRate.isChecked = false
                            btnRate.text = "Add rating"
                            btnRate.setBackgroundResource(R.color.btnPrimary)
                        }

                        btnRate.setOnCheckedChangeListener { buttonView, isChecked ->
                            if (isChecked) {
                                var ratingValue: Float = 0.0f
                                val ratingDialog: RatingDialog = RatingDialog.Builder(this)
                                    .icon(R.drawable.rating)
                                    .ratingBarColor(R.color.starColor)
                                    .title(R.string.submit_feedback)
                                    .onThresholdCleared { dialog, rating, thresholdCleared ->  ratingValue = rating}
                                    .onThresholdFailed { dialog, rating, thresholdCleared ->  ratingValue = rating }
                                    .negativeButton(text = R.string.rating_dialog_cancel, textColor = R.color.starColor, background = R.drawable.rounded_corner) { dialog ->
                                        btnRate.isChecked = false
                                        dialog.dismiss()
                                    }
                                    .positiveButton(text = R.string.ok_text, textColor = R.color.starColor, background = R.drawable.rounded_corner) {dialog ->
                                        movieDetailsViewModel.addRating(movieID, sessionID!!, ratingValue ) {result, msg ->
                                            if (result) {
                                                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                                                    .setTitle("Success")
                                                    .setMessage("Rated this movie and added to rated list.")
                                                    .setCancelable(false)
                                                    .setDarkMode(true)
                                                    .setGravity(Gravity.CENTER)
                                                    .setAnimation(DialogAnimation.SHRINK)
                                                    .setOnClickListener(object : OnDialogClickListener {
                                                        override fun onClick(dialog: AestheticDialog.Builder) {
                                                            dialog.dismiss()
                                                        }
                                                    })
                                                    .show()
                                                btnRate.isChecked = true
                                                btnRate.text = "Rated ${ratingValue.toInt()} ⭐"
                                                btnRate.setBackgroundResource(R.color.starColor)
                                            } else {
                                                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                        dialog.dismiss()
                                    }
                                    .build()
                                ratingDialog.show()
                            } else {
                                movieDetailsViewModel.deleteRating(movieID, sessionID!!) { result, msg ->
                                    if (result) {
                                        AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                                            .setTitle("Success")
                                            .setMessage("Removed this movie from rated list")
                                            .setCancelable(false)
                                            .setDarkMode(true)
                                            .setGravity(Gravity.CENTER)
                                            .setAnimation(DialogAnimation.SHRINK)
                                            .setOnClickListener(object : OnDialogClickListener {
                                                override fun onClick(dialog: AestheticDialog.Builder) {
                                                    dialog.dismiss()
                                                }
                                            })
                                            .show()
                                        btnRate.text = "Add rating"
                                        btnRate.setBackgroundResource(R.color.btnPrimary)
                                    } else {
                                        Toast.makeText(buttonView.context, msg, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }

                        btnFav.setOnCheckedChangeListener { buttonView, isChecked ->
                            if (isChecked) {
                                userViewModel.addMovieToFavoriteList(accountID,
                                    sessionID!!, movieID) { result, msg ->
                                    if (result) {
                                        AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                                            .setTitle("Success")
                                            .setMessage("Added this movie to favorite list")
                                            .setCancelable(false)
                                            .setDarkMode(true)
                                            .setGravity(Gravity.CENTER)
                                            .setAnimation(DialogAnimation.SHRINK)
                                            .setOnClickListener(object : OnDialogClickListener {
                                                override fun onClick(dialog: AestheticDialog.Builder) {
                                                    dialog.dismiss()
                                                }
                                            })
                                            .show()
                                        btnFav.text = "Favorite"
                                        btnFav.setBackgroundResource(R.color.starColor)
                                    } else {
                                        Toast.makeText(buttonView.context, msg, Toast.LENGTH_LONG).show()
                                    }
                                }
                            } else {
                                userViewModel.deleteMovieToFavoriteList(accountID,
                                    sessionID!!, movieID) { result, msg ->
                                    if (result) {
                                        AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                                            .setTitle("Success")
                                            .setMessage("Removed this movie from favorite list")
                                            .setCancelable(false)
                                            .setDarkMode(true)
                                            .setGravity(Gravity.CENTER)
                                            .setAnimation(DialogAnimation.SHRINK)
                                            .setOnClickListener(object : OnDialogClickListener {
                                                override fun onClick(dialog: AestheticDialog.Builder) {
                                                    dialog.dismiss()
                                                }
                                            })
                                            .show()
                                        btnFav.text = "Add to favorite"
                                        btnFav.setBackgroundResource(R.color.btnPrimary)
                                    } else {
                                        Toast.makeText(buttonView.context, msg, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                }
                checkAllRequestsCompleted()
            } else {
                btnFav.visibility = View.GONE
                btnRate.visibility = View.GONE
                checkAllRequestsCompleted()
            }
        }
    }
    private fun setupCastAdapter(recyclerView: RecyclerView, casts: List<Cast>) {
        recyclerView.adapter = CastAdapter(casts) { cast ->
            val intent = Intent(this, CastDetailsActivity::class.java)
            intent.putExtra("castID", cast.id.toString())
            intent.putExtra("sessionID", sessionID)
            startActivity(intent)
        }
    }

    private fun setupMovieAdapter(recyclerView: RecyclerView, movies: List<Movie>) {
        recyclerView.adapter = MovieAdapter(movies) { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("movieID", movie.id.toString())
            intent.putExtra("sessionID", sessionID)
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