package com.ijb.androidpagingstudy.repository.inMemory.byPage

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.model.RedditPost
import java.util.concurrent.Executor


/**
 * Created by bae injin on 2018. 6. 23..
 */
class PageDataSourceFactory(
        private val redditApi: Client,
        private val subredditName: String,
        private val retryExecutor: Executor
) : DataSource.Factory<String, RedditPost>() {

    val sourceLiveData = MutableLiveData<PageKeyedSubRepoDataSource>()

    override fun create(): DataSource<String, RedditPost> {
        val source = PageKeyedSubRepoDataSource(api = redditApi, subredditName = subredditName)
        sourceLiveData.postValue(source)
        return source
    }
}