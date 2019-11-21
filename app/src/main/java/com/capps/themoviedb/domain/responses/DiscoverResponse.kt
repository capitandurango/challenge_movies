package com.capps.themoviedb.domain.responses

import com.google.gson.annotations.SerializedName

/**
 * The [DiscoverResponse] keep the information for the movies.
 */
data class DiscoverResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val results: List<Movie>
)