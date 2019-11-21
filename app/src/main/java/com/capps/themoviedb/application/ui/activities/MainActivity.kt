package com.capps.themoviedb.application.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capps.themoviedb.R
import com.capps.themoviedb.application.ui.adapters.DetailedFragment
import com.capps.themoviedb.application.ui.fragments.MainFragment
import com.capps.themoviedb.application.viewsmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private val model by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) return

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, MainFragment())
        fragmentTransaction.commit()
    }

    override fun onResume() {
        super.onResume()
        addObservers()
    }

    /**
     * Observers for watch changes in the data
     */
    private fun addObservers(){
        model.getSelectItem().observe(this, Observer { movie ->
            Log.d(TAG, "Change on the activity")

            val fragment = DetailedFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(DetailedFragment.PARAM_MOVIE, movie)
            }

            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            when(fragment_container_detail) { // Decide which fragment used
                null -> fragmentTransaction.replace(
                    R.id.fragment_container,
                    fragment
                ).addToBackStack(null)
                else -> fragmentTransaction.replace(
                    R.id.fragment_container_detail,
                    fragment
                ).addToBackStack(null)
            }

            fragmentTransaction.commit()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val actionSearch = menu?.findItem(R.id.action_search)
        val searchView = actionSearch?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty())
                    model.setIsSearching(false)
                else
                    model.setIsSearching(true)

                callSearch(newText)
                return true
            }

            fun callSearch(query: String?) { //Do searching
                Log.d(TAG, "Execute query on local")
                model.filterItems(query ?: return)
            }
        })

        return true
    }

}
