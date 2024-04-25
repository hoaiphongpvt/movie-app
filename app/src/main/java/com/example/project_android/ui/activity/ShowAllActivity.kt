package com.example.project_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loadinganimation.LoadingAnimation
import com.example.project_android.R
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.ui.adapters.MovieAdapter

class ShowAllActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var loadingAnim: LoadingAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all)

        val titlePage : TextView = findViewById(R.id.titlePage)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        loadingAnim = findViewById(R.id.loadingAnim)
        recyclerView = findViewById(R.id.showAllRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3) //Hiển thị 3 item 1 hàng

        // Lấy dữ liệu từ intent
        val type = intent.getStringExtra("type")
        val movies = intent.getSerializableExtra("movies") as ArrayList<Movie>
        val sessionID = intent.getStringExtra("sessionID")

        var completedRequests = 0
        val totalRequests = 1

        fun checkAllRequestsCompleted() {
            completedRequests++
            if (completedRequests == totalRequests) {
                loadingAnim.visibility = View.GONE
            }
        }

        if (movies.isNotEmpty()) {
            // Xử lý dữ liệu theo loại
            when (type) {
                "popular" -> {
                    titlePage.text = "Popular"
                }
                "upcoming" -> {
                    titlePage.text = "Upcoming"
                }
                "topRated" -> {
                    titlePage.text = "Top Rated"
                }
                "favoriteMovie" -> {
                    titlePage.text = "Favorite Movies"
                }
                "ratedMovie" -> {
                    titlePage.text = "Rated Movies"
                }
                else -> {
                    // Xử lý trường hợp không xác định
                }
            }
            // Khởi tạo Adapter và đặt dữ liệu vào RecyclerView
            movieAdapter = MovieAdapter(movies) { movie ->
                val intent = Intent(this, MovieDetailsActivity::class.java)
                intent.putExtra("movieID", movie.id.toString())
                intent.putExtra("sessionID", sessionID)
                startActivity(intent)
            }
            recyclerView.adapter = movieAdapter
            checkAllRequestsCompleted()
        }

        // Xử lý sự kiện khi nhấn nút Back
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}
