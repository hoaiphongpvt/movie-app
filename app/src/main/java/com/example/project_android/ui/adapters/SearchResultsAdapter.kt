package com.example.project_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.remote.TheMovieDatabaseAPI

class SearchResultsAdapter (
    private val movies: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

    class SearchResultsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val BASE_IMG = TheMovieDatabaseAPI.BASE_IMG
        private val backgroundMovie: ImageView = view.findViewById(R.id.backgroundMovie)
        private val titleMovie : TextView = view.findViewById(R.id.titleMovie)

        fun bindMovie(movie: Movie) {
            Glide.with(backgroundMovie).load(BASE_IMG + movie.backdropPath).into(backgroundMovie)
            titleMovie.text = movie.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        return SearchResultsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_search, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.bindMovie((movies[position]))
        holder.itemView.setOnClickListener {
            onItemClick(movies[position])
        }
    }
}