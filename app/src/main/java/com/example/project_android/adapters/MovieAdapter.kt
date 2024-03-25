package com.example.project_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.models.entity.Movie

class MovieAdapter (
    private val movies: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val IMG_BASE = "https://image.tmdb.org/t/p/w500/"
//        private val movieTitle: TextView = view.findViewById(R.id.movie_title)
//        private val releaseDate: TextView = view.findViewById(R.id.movie_release_date)
        private val movieImg: ImageView = view.findViewById((R.id.imageView))

        fun bindMovie(movie: Movie) {
//            movieTitle.text = movie.title
//            releaseDate.text = movie.release
            Glide.with(movieImg).load(IMG_BASE + movie.poster_path).into(movieImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
       return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie((movies.get(position)))
    }
}