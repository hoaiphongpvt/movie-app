package com.example.project_android.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.project_android.R
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.data.remote.TheMovieDatabaseAPI
import com.example.project_android.ui.activity.LoginActivity
import com.example.project_android.ui.activity.ShowAllActivity
import com.example.project_android.viewmodel.UserViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener


class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var btnLogout : Button
    private lateinit var btnRatedMovies: Button
    private lateinit var btnFavoriteMovies: Button
    private lateinit var name: TextView
    private lateinit var username: TextView
    private lateinit var avatar: ImageView
    private  var sessionId : String? = null
    private var guestSessionId : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = UserViewModel(requireContext())
        btnLogout = requireView().findViewById(R.id.btnLogout)
        btnRatedMovies = requireView().findViewById(R.id.btnRatedMovies)
        btnFavoriteMovies = requireView().findViewById(R.id.btnFavoriteMovies)
        name = requireView().findViewById(R.id.name)
        username = requireView().findViewById(R.id.username)
        avatar = requireView().findViewById(R.id.avatar)

        if (arguments?.getString("sessionId")?.isNotEmpty() == true) {
            sessionId = arguments?.getString("sessionId")
        } else if(arguments?.getString("guestSessionId")?.isNotEmpty() == true) {
            guestSessionId = arguments?.getString("guestSessionId")
        }

        if (sessionId != null) {
            userViewModel.getUserDetails(20938610, sessionId!!) { user, msg ->
                if (user != null) {
                    name.text = user.name
                    username.text = user.username
                    Glide.with(avatar).load(TheMovieDatabaseAPI.BASE_PROFILE_URL + user.avatar.tmdb.avatar_path).into(avatar)
                } else {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            btnRatedMovies.visibility = View.GONE
            btnFavoriteMovies.visibility = View.GONE
        }


        if (guestSessionId != null) {
            val user = userViewModel.getUserGoogle()
            if (user != null) {
                name.text = user.displayName
                username.text = user.email
                Glide.with(avatar).load(user.photoUrl).into(avatar)
            }
        }

        //Log out
        btnLogout.setOnClickListener {
            val dialog = AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.WARNING)
            dialog
                .setTitle("Confirm Logout")
                .setMessage("Do you want to log out?")
                .setCancelable(true)
                .setDarkMode(true)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(object : OnDialogClickListener {
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        if (!sessionId.isNullOrEmpty()) {
                            userViewModel.logout(sessionId!!) { result, msg ->
                                if (result) {
                                    val intent = Intent(requireContext(), LoginActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                                }
                            }
                        } else if (!guestSessionId.isNullOrEmpty()) {
                            userViewModel.logoutGoogle { success ->
                                if (success) {
                                    val intent = Intent(requireContext(), LoginActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                })
                .show()
        }


        btnFavoriteMovies.setOnClickListener {
            userViewModel.getFavoriteMovie(20938610, sessionId!!) {favoriteMovies ->
                if (favoriteMovies != null) {
                    goToShowAll("favoriteMovie", favoriteMovies.movie)
                }
            }
        }

        btnRatedMovies.setOnClickListener {
            userViewModel.getRatedMovie(20938610, sessionId!!) {ratedMovies ->
                if (ratedMovies != null) {
                    goToShowAll("ratedMovie", ratedMovies.movie)
                }
            }
        }
    }

    private fun goToShowAll(type: String, movies: List<Movie>) {
        val intent = Intent(requireContext(), ShowAllActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("movies", ArrayList(movies))
        intent.putExtra("sessionID", sessionId)
        startActivity(intent)
    }
}