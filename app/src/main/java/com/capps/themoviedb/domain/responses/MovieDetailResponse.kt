package com.capps.themoviedb.domain.responses

import com.google.gson.annotations.SerializedName

/**
 * Mapping the detail for the movie.
 */
data class MovieDetailResponse(
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("homepage")
    val homepage: String
)