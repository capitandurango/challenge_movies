package com.capps.themoviedb.data.network

import com.capps.themoviedb.domain.responses.DiscoverResponse
import com.capps.themoviedb.domain.responses.MovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface that defines the available services.
 */
interface MovieDBApi {

    @GET("/3/discover/movie")
    suspend fun discover(
        @Header("Authorization")
        authorizationHeader: String,
        @Query("sort_by")
        sortBy: String = "popularity.des",
        @Query("page")
        page: Int = 1
    ) : DiscoverResponse


    @GET("/3/movie/{id_movie}")
    suspend fun detail(
        @Header("Authorization")
        authorizationHeader: String,
        @Path("id_movie")
        id: Long
    ) : MovieDetailResponse

}