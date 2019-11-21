package com.capps.themoviedb.application.viewsmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capps.themoviedb.data.network.APIResponse
import com.capps.themoviedb.domain.responses.DiscoverResponse
import com.capps.themoviedb.domain.responses.Movie
import com.capps.themoviedb.domain.responses.MovieDetailResponse
import com.capps.themoviedb.domain.usecases.MainUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Main View Model.
 * Note: This VM is shared between [MainActivity], [MainFragment], [DetailedFragment],
 * For shared easy data with lifecycle protection.
 */
class MainViewModel : ViewModel() {

    /**
     * Item selected for the adapter.
     */
    private val itemSelected: MutableLiveData<Movie> = MutableLiveData()

    /**
     * Item selected for the adapter.
     */
    private val items: MutableLiveData<List<Movie?>> = MutableLiveData()

    /**
     * Variable that keep the original response for the service.
     */
    private var copyItems: MutableList<Movie?> = mutableListOf()

    /**
     * For notify the adapter don't update the list of item, if the user is searching.
     */
    private val isSearching: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * We ned to keep the response for move between pages.
     */
    private var page: Int = 1

    /**
     * Use case responsible for the bussines logic.
     */
    private val mainUseCase by lazy {
        MainUseCaseImpl()
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }

    /**
     * Discover the new releases.
     */
    fun discover() {
        Log.d(TAG, "Searching movies")
        GlobalScope.launch (Dispatchers.Main) {

            when (val res = mainUseCase.discover(page = page)) {
                is APIResponse.Success -> {
                    val list = mutableListOf<Movie?>()

                    val movies =res.value.results.filterNotNull()//filter some null values

                    with(list){
                        addAll(copyItems)//copy past items
                        addAll(movies.toMutableList())//copy new items

                        items.postValue(list)//notify new values
                        copyItems = list.toMutableList()//for search local data
                    }
                    page = res.value.page
                }
            }
        }
    }

    /**
     * Method that launch the service for request detail ionformation.
     *
     * @param movie: [Movie] for looking the detail
     */
    fun detail(movie: Movie): LiveData<APIResponse<MovieDetailResponse>> {
        Log.d(TAG, "Searching the movie detail ...")
        return liveData {
            emit(
                mainUseCase.detail(movie)
            )
        }
    }

    /**
     * Put a selected item in the live data, for uploada fragments.
     */
    fun setSelectItem(item: Movie){
        itemSelected.postValue(item)
    }

    /**
     * Set a new page
     */
    fun setPage(newPage: Int){
        this.page = newPage
    }

    /**
     * Get the current page.
     */
    fun getPage() = page

    /**
     * The item selected.
     */
    fun getSelectItem() = itemSelected

    /**
     * Return the list of available items.
     */
    fun getItems() = items

    /**
     * Set the new value for searching.
     */
    fun setIsSearching(searching: Boolean){
        isSearching.postValue(searching)
    }

    /**
     * Get the status for isSearching.
     */
    fun isSearching() = isSearching

    /**
     * Function for filter items.
     * @param query: Query for search in the storage.
     */
    fun filterItems(query: String){
        when {
            query.isEmpty() -> {
                items.postValue(copyItems) // Restore original values.
            }
            query.isNotEmpty() -> {
                items.postValue(
                    copyItems?.filter { movie ->
                        movie?.title?.toUpperCase()?.contains(query.toUpperCase()) ?: false
                    }
                )
            }
        }
    }

}