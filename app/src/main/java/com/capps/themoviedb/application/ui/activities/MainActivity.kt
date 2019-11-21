package com.capps.themoviedb.application.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capps.themoviedb.R
import com.capps.themoviedb.application.viewsmodels.MainViewModel

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

        model.discover().observe(this, Observer { response ->
            Log.d(TAG, response.toString())
        })
    }
}
