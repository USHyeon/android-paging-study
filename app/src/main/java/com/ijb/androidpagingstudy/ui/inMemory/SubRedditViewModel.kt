package com.ijb.androidpagingstudy.ui.inMemory

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ijb.androidpagingstudy.repository.inMemory.RedditPostRepository

/**
 * Created by bae injin on 2018. 6. 23..
 */
class SubRedditViewModel(private val repository: RedditPostRepository) : ViewModel() {

    private val subredditName = MutableLiveData<String>()
    private val repoResult = Transformations.map(subredditName, {
        repository.postsOfSubreddit(it, 30)
    })
    val posts = Transformations.switchMap(repoResult) { it }!!

    fun showSubreddit(subreddit: String): Boolean {
        if (subredditName.value == subreddit) {
            return false
        }
        subredditName.value = subreddit
        return true
    }

    fun currentSubreddit(): String? = subredditName.value
}