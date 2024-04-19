package com.example.project_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Video
import com.example.project_android.data.remote.TheMovieDatabaseAPI

class VideoAdapter(
    private val videos: List<Video>,
    private val onItemClick: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val BASE_YT_IMG_URL = TheMovieDatabaseAPI.BASE_YT_IMG_URL
        private val thumbnail: ImageView = view.findViewById((R.id.thumbnail))

        fun bindVideo(video: Video) {
            Glide.with(thumbnail).load(BASE_YT_IMG_URL + video.key + "/hqdefault.jpg").into(thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bindVideo((videos.get(position)))
        holder.itemView.setOnClickListener {
            onItemClick(videos.get(position))
        }
    }

    override fun getItemCount(): Int = videos.size
}