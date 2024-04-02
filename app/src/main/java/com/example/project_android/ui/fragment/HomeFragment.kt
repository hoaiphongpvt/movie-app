package com.example.project_android.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.example.project_android.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.project_android.ui.adapters.MovieAdapter
import com.example.project_android.ui.adapters.MovieBannerAutoScroll
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.ui.activity.MovieDetailsActivity
import com.example.project_android.ui.activity.ShowAllActivity
import com.example.project_android.viewmodel.HomeViewModel
import java.util.Timer
import java.util.TimerTask

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setupMovieList()
    }
    private fun goToShowAll(type: String, movies: List<Movie>) {
        val intent = Intent(requireContext(), ShowAllActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("movies", ArrayList(movies))
        startActivity(intent)
    }
    private fun setupMovieList() {
        val movieList = requireView().findViewById<RecyclerView>(R.id.rm_movies_list)
        val movieListUpcoming = requireView().findViewById<RecyclerView>(R.id.rm_movies_list_upcoming)
        val movieListTopRated = requireView().findViewById<RecyclerView>(R.id.rm_movies_list_toprated)
        val viewPager = requireView().findViewById<ViewPager>(R.id.rm_movies_list_banner)

        val btnShowAllPopular : ImageButton = requireView().findViewById(R.id.showAllPopular)
        val btnShowAllUpcoming : ImageButton = requireView().findViewById(R.id.showAllUpcoming)
        val btnShowAllTopRated : ImageButton = requireView().findViewById(R.id.showAllTopRated)

        btnShowAllPopular.setOnClickListener {
            homeViewModel.getMovieData("popular") { movies ->
                goToShowAll("popular", movies)
            }
        }

        btnShowAllUpcoming.setOnClickListener {
            homeViewModel.getMovieData("upcoming") { movies ->
                goToShowAll("upcoming", movies)
            }
        }

        btnShowAllTopRated.setOnClickListener {
            homeViewModel.getMovieData("topRated") { movies ->
                goToShowAll("topRated", movies)
            }
        }

        movieList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        movieList.setHasFixedSize(true)
        movieListUpcoming.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        movieListUpcoming.setHasFixedSize(true)
        movieListTopRated.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        movieListTopRated.setHasFixedSize(true)

        homeViewModel.getMovieData("popular") { movies: List<Movie> ->
            setupMovieAdapter(movieList, movies)
        }

        homeViewModel.getMovieData("upcoming") { movies: List<Movie> ->
            setupMovieAdapter(movieListUpcoming, movies)
        }

        homeViewModel.getMovieData("topRated") { movies: List<Movie> ->
            setupMovieAdapter(movieListTopRated, movies)
        }

        homeViewModel.getMovieData("trending") { movies: List<Movie> ->
            viewPager.adapter = MovieBannerAutoScroll(movies, requireContext()) { movie ->
                val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
                intent.putExtra("movieID", movie.id.toString())
                startActivity(intent)
            }
            // Tự động trượt các item sau một khoảng thời gian
            val handler = Handler()
            val update = Runnable {
                if (viewPager.currentItem == movies.size - 1) {
                    viewPager.currentItem = 0
                } else {
                    viewPager.currentItem = viewPager.currentItem + 1
                }
            }
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            }, 1000, 4000)
        }
    }

    private fun setupMovieAdapter(recyclerView: RecyclerView, movies: List<Movie>) {
        recyclerView.adapter = MovieAdapter(movies) { movie ->
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra("movieID", movie.id.toString())
            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}