package com.capps.themoviedb.data.repositories

import android.util.Log
import com.capps.themoviedb.data.network.APIResponse
import com.capps.themoviedb.data.network.MovieDBServices
import com.capps.themoviedb.domain.responses.DiscoverResponse
import com.capps.themoviedb.domain.responses.Movie
import com.capps.themoviedb.domain.responses.MovieDetailResponse

/**
 * The [MoviesRemoteRepositoryImpl] allows to connect to the server for retreive data.
 * This is a remote repository.
 */
class MoviesRemoteRepositoryImpl {

    companion object {
        // Tag for the logger
        val TAG = this::class.java.simpleName

        // Access token for authenticate requests.
        val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MzAzNzY5NDRmM2U1ZDdjMTBjOGVkODRkYzMwMzYwOCIsInN1YiI6IjVkZDYyYzhiN2YxZDgzMDAxNTYxYjkyNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.IvRllJV6rvWsOYL5zmsEPD5_QT9uGyO9KBmY8zc-EV8"
    }

    // Instance for connection with the services.
    private val movieApi by lazy {
        MovieDBServices.getInstance()
    }

    /**
     * Method for retreive all the discover movies.
     */
    suspend fun discover(page: Int = 1)
            : APIResponse<DiscoverResponse> {
        Log.d(TAG, "Making request for remote repository....")
        return try {
            val response = movieApi.discover(
                authorizationHeader = "Bearer $ACCESS_TOKEN",
                page = page
            )

            APIResponse.Success(response)
        } catch (ex: Exception){
            val message = "Error retreiving data"
            Log.e(TAG, message)
            APIResponse.Error(message, ex)
        }
    }

    /**
     * Method for retreive the detail for the movie.
     */
    suspend fun detail(movie: Movie)
            : APIResponse<MovieDetailResponse> {
        Log.d(TAG, "Making request for remote repository....")
        return try {
            val response = movieApi.detail(
                authorizationHeader = "Bearer $ACCESS_TOKEN",
                id = movie.id
            )

            APIResponse.Success(response)
        } catch (ex: Exception){
            val message = "Error retreiving data"
            Log.e(TAG, message)
            APIResponse.Error(message, ex)
        }
    }


}