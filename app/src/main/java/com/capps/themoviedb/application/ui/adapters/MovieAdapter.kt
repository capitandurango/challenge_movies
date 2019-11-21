package com.capps.themoviedb.application.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.capps.themoviedb.R
import com.capps.themoviedb.application.extensions.imagePath
import com.capps.themoviedb.application.extensions.popularityRanking
import com.capps.themoviedb.application.extensions.releaseYear
import com.capps.themoviedb.domain.responses.Movie

/**
 * Adapter that shows the Lists of Movies.
 */
class MovieAdapter (private var items: MutableList<Movie?>,
                    private val onClickListener: (Movie) -> Unit,
                    private val refresh: (Boolean) -> Unit):
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // For enable/disable the update rows
    private var isSearching: Boolean = false

    /**
     * Change the datasource.
     */
    fun changeDataSource(items: List<Movie?>){
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    /**
     * Seter for the seraching action.
     */
    fun setSearching(searching: Boolean){
        isSearching = searching
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCell(items[position])
        if(position == items.size-1 && !isSearching) {
            refresh(true)
        } else {
            refresh(false)
        }
    }

    class ViewHolder(val view: View, val onClickListener: (Movie) -> Unit) :
        RecyclerView.ViewHolder(view) {

        /**
         * Set the info in the cell
         */
        fun bindCell(model: Movie?) {
            model ?: return
            val txtTitle = view.findViewById<TextView>(R.id.txt_title_row)
            val image = view.findViewById<AppCompatImageView>(R.id.image)
            val txtScore = view.findViewById<TextView>(R.id.txt_score)
            val txtRelease = view.findViewById<TextView>(R.id.txt_release)

            txtTitle.text = model.title
            txtScore.text = model.popularityRanking()
            txtRelease.text = model.releaseYear().toString()

            val requestOption = RequestOptions().
                centerCrop().
                apply(
                    RequestOptions.bitmapTransform(RoundedCorners(16))
                )

            Glide.with(view)
                .load(model.imagePath())
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_no_image)
                .apply(requestOption)
                .into(image)

            view.setOnClickListener {
                onClickListener(model)
            }
        }
    }
}