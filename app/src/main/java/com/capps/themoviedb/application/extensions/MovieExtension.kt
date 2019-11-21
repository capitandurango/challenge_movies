package com.capps.themoviedb.application.extensions

import com.capps.themoviedb.domain.responses.Movie
import java.text.SimpleDateFormat
import java.util.*

/**
 * Function that returns the complete path for a movie.
 */
fun Movie.imagePath() = "${com.capps.themoviedb.BuildConfig.URL_IMAGE_BASE}${posterPath}"

/**
 * Calculate the popularity percent
 */
fun Movie.popularityRanking(): String = "${(0.6 * 100).toInt()}%"

/**
 * Function that returns the year of the movie.
 */
fun Movie.releaseYear() : String {
    if(releaseDate.isNullOrEmpty()) return ""

    val format = SimpleDateFormat("yyyy-MM-dd")

    var cal = Calendar.getInstance()
    cal.time = format.parse(releaseDate)

    return cal.get(Calendar.YEAR).toString()
}