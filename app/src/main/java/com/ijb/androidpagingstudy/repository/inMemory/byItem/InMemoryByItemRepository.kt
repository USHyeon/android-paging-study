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

import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.repository.inMemory.RedditPostRepository
import java.util.concurrent.Executor

/**
 * Repository implementation that returns a Listing that loads data directly from the network
 * and uses the Item's name as the key to discover prev/next pages.
 */
class InMemoryByItemRepository(
        private val redditApi: Client,
        private val networkExecutor: Executor) : RedditPostRepository {

}

