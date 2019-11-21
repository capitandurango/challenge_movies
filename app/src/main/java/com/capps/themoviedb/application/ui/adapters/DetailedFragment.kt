package com.capps.themoviedb.application.ui.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.capps.themoviedb.R
import com.capps.themoviedb.application.extensions.imagePath
import com.capps.themoviedb.application.viewsmodels.MainViewModel
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
            val item = arguments[PARAM_MOVIE] as Movie
            Log.d(TAG, item.toString())

            val requestOption = RequestOptions().
                centerCrop().
                apply(
                    RequestOptions.bitmapTransform(RoundedCorners(16))
                )

            txt_title.text = item.title
            txt_description.text = item.overview

            Glide.with(view)
                .load(item.imagePath())
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_no_image)
                .apply(requestOption)
                .into(movie_img)
        }
    }
}