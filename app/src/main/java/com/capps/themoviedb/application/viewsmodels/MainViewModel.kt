package com.capps.themoviedb.application.viewsmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capps.themoviedb.data.network.APIResponse
import com.capps.themoviedb.domain.responses.DiscoverResponse
import com.capps.themoviedb.domain.usecases.MainUseCaseImpl

class MainViewModel : ViewModel() {

    /**
     * Use case responsible for the bussines logic.
     */
    private val mainUseCase by lazy {
        MainUseCaseImpl()
    }

    /**
     * Discover the new releases.
     */
    fun discover(): LiveData<APIResponse<DiscoverResponse>> {
        return liveData {
            emit(
                mainUseCase.discover()
            )
        }
    }

}