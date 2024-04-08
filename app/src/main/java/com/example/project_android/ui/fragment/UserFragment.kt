package com.example.project_android.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import com.example.project_android.ui.activity.LoginActivity
import com.example.project_android.viewmodel.UserViewModel

class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var btnLogout : Button
    private lateinit var btnWatchList: Button
    private lateinit var btnFavoriteMovies: Button
    private lateinit var name: TextView
    private lateinit var username: TextView
    private lateinit var avatar: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionId = arguments?.getString("sessionId")
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        btnLogout = requireView().findViewById(R.id.btnLogout)
        btnWatchList = requireView().findViewById(R.id.watchlist)
        btnFavoriteMovies = requireView().findViewById(R.id.favoriteMovies)
        name = requireView().findViewById(R.id.name)
        username = requireView().findViewById(R.id.username)
        avatar = requireView().findViewById(R.id.avatar)

        if (sessionId != null) {
            userViewModel.getUserDetails(20938610, sessionId) {user, msg ->
                if (user != null) {
                    name.text = user.name
                    username.text = user.username
                    Glide.with(avatar).load(TheMovieDatabaseAPI.BASE_PROFILE_URL + user.avatar.tmdb.avatar_path).into(avatar)
                } else {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            }
        }

        btnLogout.setOnClickListener {
            userViewModel.logout(sessionId!!) { result, msg ->
                if (result) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}