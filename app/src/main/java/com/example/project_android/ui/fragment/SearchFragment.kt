package com.example.project_android.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
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
import com.example.loadinganimation.LoadingAnimation
import com.example.project_android.R
import com.example.project_android.data.models.entity.Movie
import com.example.project_android.ui.activity.MovieDetailsActivity
import com.example.project_android.ui.adapters.SearchResultsAdapter
import com.example.project_android.viewmodel.SearchViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener

class SearchFragment : Fragment() {

    private lateinit var btnSearch : ImageButton
    private lateinit var txtQuery : EditText
    private lateinit var searchViewModel : SearchViewModel
    private lateinit var txtResult: TextView
    private lateinit var searchResultsRecyclerview : RecyclerView
    private var sessionId : String? = null
    private var guestSessionId : String? = null
    private lateinit var loadingAnim: LoadingAnimation
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
        loadingAnim = requireView().findViewById(R.id.loadingAnim)
        loadingAnim.visibility = View.GONE
        if (arguments?.getString("sessionId")?.isNotEmpty() == true) {
            sessionId = arguments?.getString("sessionId")
        } else if(arguments?.getString("guestSessionId")?.isNotEmpty() == true) {
            guestSessionId = arguments?.getString("guestSessionId")
        }

        fun getResults(query : String) {
            searchViewModel.searchMovie(query) { movies ->
                if (movies.isNotEmpty()) {
                    txtResult.text = "Results for '${query}'"
                    setupSearchResultsAdapter(searchResultsRecyclerview, movies)
                    loadingAnim.visibility = View.GONE
                }
                else {
                    loadingAnim.visibility = View.GONE
                    txtResult.text = null
                    AestheticDialog.Builder(requireActivity(), DialogStyle.EMOTION, DialogType.WARNING)
                        .setTitle("Warning")
                        .setMessage("There are no movies with this name!!!")
                        .setDarkMode(true)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SHRINK)
                        .setOnClickListener(object : OnDialogClickListener {
                            override fun onClick(dialog: AestheticDialog.Builder) {
                                dialog.dismiss()
                            }
                        })
                        .show()
                }
            }
        }

        btnSearch.setOnClickListener {
            //Hide keyboard
            val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            loadingAnim.visibility = View.VISIBLE
            val query = txtQuery.text.toString()
            if (query.isNotEmpty()) {
                getResults(query)
                txtQuery.text = null
            } else {
                loadingAnim.visibility = View.GONE
                AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.WARNING)
                    .setTitle("Warning")
                    .setMessage("Please enter a name!")
                    .setDarkMode(true)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(DialogAnimation.SHRINK)
                    .setOnClickListener(object : OnDialogClickListener {
                        override fun onClick(dialog: AestheticDialog.Builder) {
                            dialog.dismiss()
                        }
                    })
                    .show()
            }
        }
    }

    private fun setupSearchResultsAdapter(recyclerView: RecyclerView, movies: List<Movie>) {
        recyclerView.adapter = SearchResultsAdapter(movies) { movie ->
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra("movieID", movie.id.toString())
            intent.putExtra("sessionID", sessionId)
            startActivity(intent)
        }
    }
}