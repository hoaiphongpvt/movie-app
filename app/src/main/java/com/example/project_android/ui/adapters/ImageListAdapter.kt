package com.example.project_android.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Image
import com.example.project_android.data.remote.TheMovieDatabaseAPI

class ImageListAdapter (
    private val images: List<Image>,
) : RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder>()  {
    class ImageListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val BASE_IMG = TheMovieDatabaseAPI.BASE_IMG

        private val imageCast: ImageView = view.findViewById(R.id.image)

        fun bindImg(image: Image) {
            Glide.with(imageCast).load(BASE_IMG + image.filePath).into(imageCast)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListAdapter.ImageListViewHolder {
        return ImageListAdapter.ImageListViewHolder(
            LayoutInflater  .from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.bindImg((images.get(position)))
    }
}
