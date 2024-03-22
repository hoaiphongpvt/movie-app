package com.example.project_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_android.models.Movie
import com.example.project_android.models.MovieRespone
import com.example.project_android.services.MovieApiInterface
import com.example.project_android.services.MovieApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val movieList = findViewById<RecyclerView>(R.id.rm_movies_list)

        movieList.layoutManager = LinearLayoutManager(this)
        movieList.setHasFixedSize(true)
        getMovieData { movies: List<Movie> -> movieList.adapter = MovieAdapter(movies) }
    }


    private fun getMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiServices.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieRespone> {
            override fun onResponse(call: Call<MovieRespone>, response: Response<MovieRespone>) {
                return callback(response.body()!!.movie)
            }

            override fun onFailure(call: Call<MovieRespone>, t: Throwable) {

            }

        })
    }
}