/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ijb.androidpagingstudy.repository.inMemory.byItem

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.model.RedditPost
import com.ijb.androidpagingstudy.repository.inMemory.RedditPostRepository
import java.util.concurrent.Executor

/**
 * Repository implementation that returns a Listing that loads data directly from the network
 * and uses the Item's name as the key to discover prev/next pages.
 */
class InMemoryByItemRepository(
        private val redditApi: Client,
        private val networkExecutor: Executor) : RedditPostRepository {

    override fun postsOfSubreddit(subReddit: String, pageSize: Int): LiveData<PagedList<RedditPost>> {

        val sourceFactory = SubRedditDataSourceFactory(redditApi, subReddit, networkExecutor)
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPageSize(pageSize)
                .build()

        val pagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
                // provide custom executor for network requests, otherwise it will default to
                // Arch Components' IO pool which is also used for disk access
                .setFetchExecutor(networkExecutor)
                .build()

        return pagedList
    }

}

