package com.example.project_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.models.entity.Movie
import com.example.project_android.utils.convertDateFormat

class MovieBannerAutoScroll(
    private val movies: List<Movie>,
    private val context: Context,
    private val IMG_BASE: String = "https://image.tmdb.org/t/p/w500/"
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.movie_item_banner, container, false)
        val movie = movies[position]

        val movieTitle: TextView = view.findViewById(R.id.titleText)
        val voteCount: TextView = view.findViewById(R.id.vote_count)
        val movieReleaseDate: TextView = view.findViewById(R.id.release_date)
        val movieImg: ImageView = view.findViewById(R.id.image)

        movieTitle.text = movie.title
        movieReleaseDate.text = movie.release.convertDateFormat()
        voteCount.text = "${movie.vote_count} votes"
        Glide.with(context).load(IMG_BASE + movie.backdrop_path).into(movieImg)

        container.addView(view)

        return view
    }

    override fun getCount(): Int {
        return movies.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}