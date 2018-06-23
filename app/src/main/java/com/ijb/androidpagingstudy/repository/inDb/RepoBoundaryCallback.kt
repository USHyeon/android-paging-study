package com.ijb.androidpagingstudy.repository.inDb

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.api.searchRepos
import com.ijb.androidpagingstudy.db.GithubLocalCache
import com.ijb.androidpagingstudy.model.Repo

/**
 * Created by bae injin on 2018. 6. 20..
 */
class RepoBoundaryCallback(
        private val query: String,
        private val service: Client,
        private val cache: GithubLocalCache
) : PagedList.BoundaryCallback<Repo>() {

    // keep the last requested page.
    // When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Repo) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
            cache.insert(repos, {
                lastRequestedPage++
                isRequestInProgress = false
            })
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}