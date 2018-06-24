package com.ijb.androidpagingstudy.repository.inMemory.byPage

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.model.RedditPost
import retrofit2.Call
import retrofit2.Response

/**
 * Created by bae injin on 2018. 6. 22..
 */
class PageKeyedSubRepoDataSource(
        private val api: Client,
        private val subredditName: String
) : PageKeyedDataSource<String, RedditPost>() {
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RedditPost>) {
        val request = api.getTop(
                subreddit = subredditName,
                limit = params.requestedLoadSize)

        val response = request.execute()

        val data = response.body()?.data
        val items = data?.children?.map { it.data } ?: emptyList()
        callback.onResult(items, data?.before, data?.after)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        api.getTopAfter(subreddit = subredditName,
                after = params.key,
                limit = params.requestedLoadSize).enqueue(
                object : retrofit2.Callback<Client.ListingResponse> {
                    override fun onFailure(call: Call<Client.ListingResponse>, t: Throwable) {
                        Log.e(TAG, "network error: ${t.message}")
                    }

                    override fun onResponse(call: Call<Client.ListingResponse>, response: Response<Client.ListingResponse>) {
                        if (response.isSuccessful) {

                            val data = response.body()?.data
                            val items = data?.children?.map { it.data } ?: emptyList()
                            callback.onResult(items, data?.after)
                        } else {
                            Log.e(TAG, "error code: ${response.code()}")
                        }
                    }

                }
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        // ignored, since we only ever append to our initial load
    }

    companion object {
        private const val TAG = "PageKeyed"
    }

}