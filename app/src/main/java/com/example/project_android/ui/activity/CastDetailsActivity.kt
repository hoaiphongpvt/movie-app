package com.example.project_android.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.project_android.R
import com.example.project_android.data.models.entity.Cast
import com.example.project_android.data.models.entity.Movie

class CastDetailsActivity : AppCompatActivity() {

    private lateinit var titlePage: TextView
    private lateinit var btnBack: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_details)

        val cast = intent.getParcelableExtra<Cast>("cast")
        titlePage = findViewById(R.id.titlePage)
        btnBack = findViewById(R.id.backButton)
        if (cast != null) {
            titlePage.text = cast.name
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}