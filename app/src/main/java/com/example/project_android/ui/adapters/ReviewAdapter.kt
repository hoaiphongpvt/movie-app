package com.example.project_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Review
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import com.example.project_android.utils.convertDateFormat

class ReviewAdapter (
    private val reviews : List<Review>
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val BASE_IMG = TheMovieDatabaseAPI.BASE_PROFILE_URL
        private val imgUser : ImageView = view.findViewById(R.id.imgUser)
        private val username : TextView = view.findViewById(R.id.username)
        private val content : TextView = view.findViewById(R.id.content)
        private val createAt : TextView = view.findViewById(R.id.createAt)
        private val rating : RatingBar = view.findViewById(R.id.ratingBarReview)

        fun bindReview(review: Review) {
            Glide.with(imgUser).load(BASE_IMG + review.authorDetails.avatarPath).into(imgUser)
            username.text = review.author
            content.text = review.content
            createAt.text = review.createdAt.convertDateFormat()
            rating.rating = (review.authorDetails.rating?.div(2))?.toFloat() ?: 1.0f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false))
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bindReview(reviews[position])
    }
}