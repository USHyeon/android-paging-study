package com.ijb.androidpagingstudy.ui.inMemory.byPage

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.ijb.androidpagingstudy.model.RedditPost
import com.ijb.androidpagingstudy.repository.inMemory.byPage.PageRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Created by bae injin on 2018. 6. 25..
 */
class PageViewModel(repository: PageRepository) : ViewModel() {

    private val subreddit: String = "kotlin"

    val repoList: Flowable<PagedList<RedditPost>> = RxPagedListBuilder(
           repository.postsOfSubreddit(subreddit, 1), 2
    ).buildFlowable(BackpressureStrategy.LATEST)
}