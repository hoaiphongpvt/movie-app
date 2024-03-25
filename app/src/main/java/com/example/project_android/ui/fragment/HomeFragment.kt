package com.example.project_android.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_android.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_android.adapters.MovieAdapter
import com.example.project_android.models.entity.Movie
import com.example.project_android.models.network.MovieRespone
import com.example.project_android.services.MovieApiInterface
import com.example.project_android.services.MovieApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        setupMovieList()
    }

    private fun setupMovieList() {
        val movieList = requireView().findViewById<RecyclerView>(R.id.rm_movies_list)
        val movieListUpcoming = requireView().findViewById<RecyclerView>(R.id.rm_movies_list_upcoming)
        val movieListTopRated = requireView().findViewById<RecyclerView>(R.id.rm_movies_list_toprated)

        movieList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        movieList.setHasFixedSize(true)
        movieListUpcoming.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        movieListUpcoming.setHasFixedSize(true)
        movieListTopRated.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        movieListTopRated.setHasFixedSize(true)

        getMovieData("popular") { movies: List<Movie> ->
            movieList.adapter = MovieAdapter(movies)
        }

        getMovieData("upcoming") { movies: List<Movie> ->
            movieListUpcoming.adapter = MovieAdapter(movies)
        }

        getMovieData("topRated") { movies: List<Movie> ->
            movieListTopRated.adapter = MovieAdapter(movies)
        }
    }

    private fun getMovieData(type: String, callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiServices.getInstance().create(MovieApiInterface::class.java)
        val call: Call<MovieRespone> = when (type) {
            "popular" -> apiService.getPopularMovieList()
            "upcoming" -> apiService.getUpcomingMovieList()
            "topRated" -> apiService.getTopRatedMovieList()
            // Thêm các trường hợp khác nếu cần
            else -> apiService.getPopularMovieList() // Mặc định lấy dữ liệu phim phổ biến nếu không xác định được loại
        }
        call.enqueue(object : Callback<MovieRespone> {
            override fun onResponse(call: Call<MovieRespone>, response: Response<MovieRespone>) {
                if (response.isSuccessful) {
                    callback(response.body()?.movie ?: emptyList())
                } else {
                    // Xử lý khi lấy dữ liệu thất bại
                }
            }

            override fun onFailure(call: Call<MovieRespone>, t: Throwable) {
                // Xử lý khi gọi API thất bại
            }
        })
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