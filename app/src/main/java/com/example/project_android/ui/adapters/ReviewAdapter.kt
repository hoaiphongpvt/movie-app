package com.example.project_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
    private val reviews : List<Review>,
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val expandedStates = MutableList(reviews.size) { false }

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val BASE_IMG = TheMovieDatabaseAPI.BASE_PROFILE_URL
        private val imgUser : ImageView = view.findViewById(R.id.imgUser)
        private val username : TextView = view.findViewById(R.id.username)
        private val content : TextView = view.findViewById(R.id.content)
        val btnShowAllReview : Button = view.findViewById(R.id.btnShowAllReview)
        private val createAt : TextView = view.findViewById(R.id.createAt)
        private val rating : RatingBar = view.findViewById(R.id.ratingBarReview)

        fun bindReview(review: Review, expanded: Boolean) {
            Glide.with(imgUser).load(BASE_IMG + review.authorDetails.avatarPath).into(imgUser)
            username.text = review.author
            content.text = review.content
            content.maxLines = if (expanded) Int.MAX_VALUE else 6
            btnShowAllReview.text = if (expanded) "Show less" else "Show all"
            createAt.text = review.createdAt.convertDateFormat()
            rating.rating = (review.authorDetails.rating?.div(2))?.toFloat() ?: 1.0f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false))
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        val expanded = expandedStates[position]
        holder.bindReview(review, expanded)

        holder.btnShowAllReview.setOnClickListener {
            expandedStates[position] = !expandedStates[position]
            notifyItemChanged(position)

        }
    }
}