package com.capps.themoviedb.application.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capps.themoviedb.R
import com.capps.themoviedb.application.ui.adapters.MovieAdapter
import com.capps.themoviedb.application.viewsmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {
        val TAG = this::class.java.simpleName
    }

    // Define the adapter
    lateinit var movieAdapter: MovieAdapter

    // View model shared by fragments and activities.
    private val model by lazy {
        activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUI()
        setUpObservers()
        fetchData()
    }

    /**
     * Configure the intial values from the screen.
     */
    private fun setUpUI(){
        movieAdapter = MovieAdapter(mutableListOf(), { click ->
            Log.d(TAG, "Click on item ${click}")
            model.setSelectItem(click)
        }, { refresh ->
            when(refresh) {
                true -> {
                    Log.d(TAG, "refreshing more data")
                    model.setPage(
                        newPage = model.getPage() + 1
                    )
                    model.discover()
                }
            }
        })

        recycler_movies.apply {
            layoutManager = LinearLayoutManager(this@MainFragment.context)
            adapter = movieAdapter
        }
    }

    /**
     * Inicilaize the rest of observers.
     */
    private fun setUpObservers(){
        model.isSearching().observe(this, Observer{ isSearching ->
            movieAdapter.setSearching(isSearching)
        })
    }

    /**
     * Retreive the information for fill the recycler view.
     */
    private fun fetchData(){
        swipe_container.isRefreshing = true

        model.discover()
        model.getItems().observe(this, Observer { response ->
            Log.d(TAG, "response success $response")
            swipe_container.isRefreshing = false

            Log.d(TAG, "Return Success")

            movieAdapter.changeDataSource(response)
        })
    }


}