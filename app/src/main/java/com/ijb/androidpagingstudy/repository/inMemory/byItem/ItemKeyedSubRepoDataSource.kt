package com.ijb.androidpagingstudy.repository.inMemory.byItem

import android.arch.paging.ItemKeyedDataSource
import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.model.RedditPost

/**
 * Created by bae injin on 2018. 6. 22..
 */
class ItemKeyedSubRepoDataSource(
        private val api: Client,
        private val subredditName: String
) : ItemKeyedDataSource<String, RedditPost>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<RedditPost>) {

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {
        // ignored, since we only ever append to our initial load
    }

    override fun getKey(item: RedditPost): String = item.name

    companion object {
        private const val TAG = "ItemKeyed"
        private const val DEFAULT_QUERY = "kotlin"
    }

}