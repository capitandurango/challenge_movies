package com.capps.themoviedb.application.ui.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.capps.themoviedb.R
import com.capps.themoviedb.application.extensions.imagePath
import com.capps.themoviedb.application.viewsmodels.MainViewModel
import com.capps.themoviedb.data.network.APIResponse
import com.capps.themoviedb.domain.responses.Movie
import kotlinx.android.synthetic.main.fragment_detailed.*

class DetailedFragment : Fragment() {

    companion object {
        private val TAG = this::class.java.simpleName

        // The variable for shared information
        val PARAM_MOVIE = "const_param_movie"
    }

    // View model attached to the activity.
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
        return inflater.inflate(R.layout.fragment_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { arguments ->
            val movie = arguments[PARAM_MOVIE] as Movie
            Log.d(TAG, movie.toString())

            fillUI(
                movie = movie,
                view = view
            )
        }
    }

    /**
     * Print the information in the screen.
     * @param movie: The [Movie] information.
     * @param view: The [View] for init glide.
     */
    private fun fillUI(movie: Movie, view: View) {
        model.detail(movie).observe(this, Observer {
                detail ->
            Log.d(TAG, "Detailed information retreive $detail")

            when(detail){
                is APIResponse.Success -> {
                    txt_homepage.text = detail.value.homepage
                    txt_runtime.text = getString(R.string.common_minutes).format(detail.value.runtime)
                }
                is APIResponse.Error -> {
                    txt_homepage.text = getString(R.string.error_no_info_homepage)
                    txt_runtime.text = getString(R.string.error_no_info_runtime)
                }
            }
        })

        val requestOption = RequestOptions().
            centerCrop().
            apply(
                RequestOptions.bitmapTransform(RoundedCorners(16))
            )

        txt_title.text = movie.title
        txt_description.text = movie.overview

        Glide.with(view)
            .load(movie.imagePath())
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_no_image)
            .apply(requestOption)
            .into(movie_img)
    }
}