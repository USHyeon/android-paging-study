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

package com.ijb.androidpagingstudy.ui.inMemory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.ijb.androidpagingstudy.R
import kotlinx.android.synthetic.main.activity_reddit.*

/**
 * A list activity that shows reddit posts in the given sub-reddit.
 * <p>
 * The intent arguments can be modified to make it use a different repository (see MainActivity).
 */
class RedditActivity : AppCompatActivity() {
    companion object {
        const val KEY_SUBREDDIT = "subreddit"
        const val DEFAULT_SUBREDDIT = "androiddev"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit)

        initAdapter()
        initSearch()

    }

    private fun initAdapter() {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun initSearch() {
        input.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatedSubredditFromInput()
                true
            } else {
                false
            }
        })
        input.setOnKeyListener({ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatedSubredditFromInput()
                true
            } else {
                false
            }
        })
    }

    private fun updatedSubredditFromInput() {

    }
}
