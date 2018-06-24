package com.ijb.androidpagingstudy.ui.inMemory.byPage

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ijb.androidpagingstudy.R
import com.ijb.androidpagingstudy.api.Client
import com.ijb.androidpagingstudy.repository.inMemory.byPage.InMemoryByPageRepository
import com.ijb.androidpagingstudy.ui.inMemory.PostsAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_reddit.*
import java.util.concurrent.Executors

/**
 * Created by bae injin on 2018. 6. 25..
 */
class PageActivity : AppCompatActivity() {
    private lateinit var adapter: PostsAdapter
    private lateinit var viewModel: PageViewModel
    private val disposable = CompositeDisposable()

    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit)

        viewModel = getViewModel()
        adapter = PostsAdapter()
        list.adapter = adapter
    }

    private fun getViewModel(): PageViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = InMemoryByPageRepository(redditApi = Client.create(Client.BASE_URL_REDDIT),
                        networkExecutor = NETWORK_IO)
                @Suppress("UNCHECKED_CAST")
                return PageViewModel(repo) as T
            }
        })[PageViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        disposable.add(viewModel.repoList.subscribe({
            flowableList -> adapter.submitList(flowableList)
        }))
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

}