package com.capps.themoviedb.domain.usecases

import com.capps.themoviedb.data.network.APIResponse
import com.capps.themoviedb.data.repositories.MoviesRemoteRepositoryImpl
import com.capps.themoviedb.domain.responses.DiscoverResponse

class MainUseCaseImpl {

    companion object {

        // Tag for this class
        val TAG = this::class.java.simpleName
    }

    // The currrent repository for looking data, at this moment all is remote
    private val repository by lazy {
        MoviesRemoteRepositoryImpl()
    }

    /**
     * Call the service for retreives the new movies.
     *
     */
    suspend fun discover(): APIResponse<DiscoverResponse>
            = repository.discover()

}