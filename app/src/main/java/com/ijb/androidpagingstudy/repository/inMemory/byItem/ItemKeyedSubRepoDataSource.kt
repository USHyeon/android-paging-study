package com.ijb.androidpagingstudy.repository.inMemory.byItem

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.model.RedditPost
import retrofit2.Call
import retrofit2.Response

/**
 * Created by bae injin on 2018. 6. 22..
 */
class ItemKeyedSubRepoDataSource(
        private val api: Client,
        private val subredditName: String
) : ItemKeyedDataSource<String, RedditPost>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<RedditPost>) {
        val request = api.getTop(
                subreddit = subredditName,
                limit = params.requestedLoadSize)

        val response = request.execute()
        val items = response.body()?.data?.children?.map { it.data } ?: emptyList()
        callback.onResult(items)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {

        api.getTopAfter(subreddit = subredditName,
                after = params.key,
                limit = params.requestedLoadSize).enqueue(
                object : retrofit2.Callback<Client.ListingResponse> {
                    override fun onFailure(call: Call<Client.ListingResponse>, t: Throwable) {
                        Log.e(TAG, "network error: ${t.message}")
                    }

                    override fun onResponse(call: Call<Client.ListingResponse>, response: Response<Client.ListingResponse>) {
                        if (response.isSuccessful) {

                            val items = response.body()?.data?.children?.map { it.data } ?: emptyList()
                            callback.onResult(items)
                        } else {
                            Log.e(TAG, "error code: ${response.code()}")
                        }
                    }

                }
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {
        // ignored, since we only ever append to our initial load
    }

    override fun getKey(item: RedditPost): String = item.name

    companion object {
        private const val TAG = "ItemKeyed"
    }

}