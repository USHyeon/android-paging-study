package com.ijb.androidpagingstudy.repository.inMemory.byPage

import android.arch.paging.DataSource
import com.ijb.androidpagingstudy.model.RedditPost

/**
 * Created by bae injin on 2018. 6. 25..
 */
interface PageRepository {

    fun postsOfSubreddit(subReddit: String, pageSize: Int): DataSource.Factory<String, RedditPost>

}