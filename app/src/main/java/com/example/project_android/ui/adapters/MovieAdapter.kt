package com.example.project_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.remote.TheMovieDatabaseAPI

class MovieAdapter(
    private val movies: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val BASE_IMG = TheMovieDatabaseAPI.BASE_IMG
        private val movieImg: ImageView = view.findViewById((R.id.imageView))
        fun bindMovie(movie: Movie) {
            Glide.with(movieImg).load(BASE_IMG + movie.posterPath).into(movieImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
       return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie((movies.get(position)))
        holder.itemView.setOnClickListener {
            onItemClick(movies.get(position))
        }
    }
}