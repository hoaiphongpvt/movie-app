package com.example.project_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Cast
import com.example.project_android.data.models.entity.Credit
import com.example.project_android.data.models.entity.Image
import com.example.project_android.viewmodel.CastDetailsViewModel
import com.example.project_android.data.remote.TheMovieDatabaseAPI.BASE_IMG
import com.example.project_android.ui.adapters.CreditsListAdapter
import com.example.project_android.ui.adapters.ImageListAdapter

class CastDetailsActivity : AppCompatActivity() {
    private lateinit var castDetailsViewModel: CastDetailsViewModel
    private lateinit var titlePage: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var personImg: ImageView
    private lateinit var personName: TextView
    private lateinit var knownForDepartment: TextView
    private lateinit var personBiography: TextView
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var knownForRecyclerView: RecyclerView
    private var sessionID : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_details)

        castDetailsViewModel = ViewModelProvider(this).get(CastDetailsViewModel::class.java)
        val castID = intent.getStringExtra("castID")
        sessionID = intent.getStringExtra("sessionID")
        titlePage = findViewById(R.id.titlePage)
        btnBack = findViewById(R.id.backButton)
        personImg = findViewById(R.id.personImage)
        personName = findViewById(R.id.personName)
        knownForDepartment = findViewById(R.id.knownForDepartment)
        personBiography = findViewById(R.id.personBiography)
        photosRecyclerView = findViewById(R.id.photosRecyclerView)
        knownForRecyclerView = findViewById(R.id.knownForRecyclerView)

        if (castID != null) {
            castDetailsViewModel.getPersonData(castID) { personDetails ->
                if (personDetails != null) {
                    titlePage.text = personDetails.name
                    Glide.with(personImg).load(BASE_IMG + personDetails.profilePath).into(personImg)
                    personName.text = personDetails.name
                    knownForDepartment.text = personDetails.knownForDepartment
                    personBiography.text = personDetails.biography
                }
            }

            castDetailsViewModel.getListImagesPerson(castID) {images: List<Image> ->
                setupImageAdapter(photosRecyclerView, images)
            }

            castDetailsViewModel.getCombinedCredits(castID) {credits: List<Credit> ->
                setupCreditsAdapter(knownForRecyclerView, credits)
            }
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupImageAdapter(recyclerView: RecyclerView, images: List<Image>) {
        recyclerView.adapter = ImageListAdapter(images)
    }

    private fun setupCreditsAdapter(recyclerView: RecyclerView, credits: List<Credit>) {
        recyclerView.adapter = CreditsListAdapter(credits) { credit ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("movieID", credit.id.toString())
            intent.putExtra("sessionID", sessionID)
            startActivity(intent)
        }
    }
}