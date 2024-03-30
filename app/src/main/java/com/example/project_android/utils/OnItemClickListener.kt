package com.example.project_android.utils

import com.example.project_android.data.models.entity.Movie

interface OnItemClickListener {
    fun onItemClick(movie: Movie)
}