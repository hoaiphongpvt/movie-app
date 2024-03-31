package com.example.project_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Cast
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.remote.TheMovieDatabaseAPI

class CastAdapter (
    private val casts: List<Cast>,
    private val onItemClick: (Cast) -> Unit
    ) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

        class CastViewHolder(view: View) : RecyclerView.ViewHolder(view){
            private val BASE_IMG = TheMovieDatabaseAPI.BASE_IMG
            private val castImg: ImageView = view.findViewById((R.id.castImage))
            private val castName: TextView = view.findViewById(R.id.castName)

            fun bindMovie(cast: Cast) {
                castName.text = cast.name
                Glide.with(castImg).load(BASE_IMG + cast.profilePath).into(castImg)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
            return CastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false))
        }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindMovie((casts.get(position)))
        holder.itemView.setOnClickListener {
            onItemClick(casts.get(position))
        }
    }

    override fun getItemCount(): Int = casts.size

}