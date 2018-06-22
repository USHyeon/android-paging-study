package com.ijb.androidpagingstudy.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.ijb.androidpagingstudy.repository.inDb.GithubRepository
import com.ijb.androidpagingstudy.model.Repo
import com.ijb.androidpagingstudy.model.RepoSearchResult

/**
 * ViewModel for the [SearchRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val repos: LiveData<PagedList<Repo>> = Transformations.switchMap(repoResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult,
            { it -> it.networkErrors })

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value
}