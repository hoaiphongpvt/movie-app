package com.example.project_android.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_android.R
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.ui.activity.MovieDetailsActivity
import com.example.project_android.ui.adapters.SearchResultsAdapter
import com.example.project_android.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var btnSearch : ImageButton
    private lateinit var txtQuery : EditText
    private lateinit var searchViewModel : SearchViewModel
    private lateinit var txtResult: TextView
    private lateinit var searchResultsRecyclerview : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSearch = requireView().findViewById(R.id.btnSearch)
        txtQuery = requireView().findViewById(R.id.txtQuery)
        txtResult = requireView().findViewById(R.id.txtResult)
        searchResultsRecyclerview = requireView().findViewById(R.id.searchResultsRecyclerview)
        searchResultsRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        searchResultsRecyclerview.setHasFixedSize(true)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        btnSearch.setOnClickListener {
            //Hide keyboard
            val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            val query = txtQuery.text.toString()
            if (query.isNotEmpty()) {
                txtResult.text = "Results for '${txtQuery.text}'"
                getResults(query)
                txtQuery.text = null
            }
        }
    }

    private fun getResults(query : String) {
        searchViewModel.searchMovie(query) { movies ->
            setupSearchResultsAdapter(searchResultsRecyclerview, movies)
        }
    }

    private fun setupSearchResultsAdapter(recyclerView: RecyclerView, movies: List<Movie>) {
        recyclerView.adapter = SearchResultsAdapter(movies) { movie ->
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra("movieID", movie.id.toString())
            startActivity(intent)
        }
    }
}