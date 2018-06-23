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

package com.ijb.androidpagingstudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ijb.androidpagingstudy.ui.inDb.SearchRepositoriesActivity
import com.ijb.androidpagingstudy.ui.inMemory.RedditActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * chooser activity for the demo.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        withDatabase.setOnClickListener {
            startActivity<SearchRepositoriesActivity>()
        }
        networkOnly.setOnClickListener {
            startActivity<RedditActivity>()
        }
        networkOnlyWithPageKeys.setOnClickListener {

        }
    }

    private inline fun <reified T : Any> Context.startActivity() {
        startActivity(Intent(this, T::class.java))
    }
}
