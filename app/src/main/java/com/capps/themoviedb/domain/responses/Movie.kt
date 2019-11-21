package com.capps.themoviedb.domain.responses

import com.google.gson.annotations.SerializedName

/**
 * The [Movie] keep the information for a movie.
 *
 * Note: This class also could be in the file [DiscoverResponse.kt]
 * but for better organization I created many files .kt.
 */
data class Movie(
    @SerializedName("id")
    val id: Long,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("release_date")
    val releaseDate: String
)