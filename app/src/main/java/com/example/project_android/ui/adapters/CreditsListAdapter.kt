package com.example.project_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Credit
import com.example.project_android.data.remote.TheMovieDatabaseAPI

class CreditsListAdapter(
    private val credits: List<Credit>,
    private val onItemClick: (Credit) -> Unit
) : RecyclerView.Adapter<CreditsListAdapter.CreditsListViewHolder>() {

    class CreditsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val BASE_IMG = TheMovieDatabaseAPI.BASE_IMG
        private val imageView: ImageView = view.findViewById(R.id.imageView)

        fun bindCredit(credit: Credit) {
            Glide.with(imageView).load(BASE_IMG + credit.posterPath).into(imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsListAdapter.CreditsListViewHolder {
        return CreditsListAdapter.CreditsListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun getItemCount(): Int = credits.size

    override fun onBindViewHolder(holder: CreditsListViewHolder, position: Int) {
        holder.bindCredit((credits.get(position)))
        holder.itemView.setOnClickListener {
            onItemClick(credits.get(position))
        }
    }
}