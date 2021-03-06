package com.ijb.androidpagingstudy.repository.inDb

import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.db.GithubLocalCache
import com.ijb.androidpagingstudy.model.RepoSearchResult

/**
 * Repository class that works with local and remote data sources.
 */
class GithubRepository(
        private val service: Client,
        private val cache: GithubLocalCache
) {

    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): RepoSearchResult {
        Log.d("GithubRepository", "New query: $query")

        // Get data from the local cache
        val dataSourceFactory = cache.reposByName(query)

        // Construct the boundary callback
        val boundaryCallback = RepoBoundaryCallback(query, service, cache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        // Get the network errors exposed by the boundary callback
        return RepoSearchResult(data, networkErrors)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}