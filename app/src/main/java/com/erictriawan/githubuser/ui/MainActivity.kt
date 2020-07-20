package com.erictriawan.githubuser.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erictriawan.githubuser.R
import com.erictriawan.githubuser.extension.afterTextChanged
import com.erictriawan.githubuser.utils.getStr
import com.erictriawan.githubuser.utils.hideKeyboard
import com.erictriawan.githubuser.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModel()
    private lateinit var userSearchAdapter: UserAdapter
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        registerLiveData()
    }

    private fun setupView() {
        et_search.afterTextChanged {
            if (it.isNotEmpty()) {
                viewModel.search(it)
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        userSearchAdapter = UserAdapter()
        rvList.apply {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            val scrollListener = object : RecyclerView.OnScrollListener() {


                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (viewModel.isLoading.value == true || viewModel.noMoreItem.value == true) {
                        return
                    }


                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()


                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                            viewModel.loadMore()
                            Log.e("cek","LoadMore")
                        }


                }
            }
            adapter = userSearchAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(scrollListener)
            setOnTouchListener(recyclerViewTouchListener)
        }
    }

    private val recyclerViewTouchListener = View.OnTouchListener { _, _ ->
        hideKeyboard(this@MainActivity)
        false
    }

    private fun registerLiveData() {
        viewModel.apply {
            loadingError.observe(this@MainActivity, Observer { error ->
                error ?: return@Observer
                val errorMsg = error.message
                loadingError.value = null
                toast(if (errorMsg.isNullOrEmpty()) getStr(R.string.something_went_wrong) else errorMsg)
            })
            userList.observe(this@MainActivity, Observer { githubUsers ->
                run {
                    userSearchAdapter.updateUsers(githubUsers)
                    if (githubUsers.isEmpty() && isLoading.value == false) {
                        toast(R.string.no_result)
                    }
                }
            })
            isLoading.observe(this@MainActivity, Observer {
                if (isLoading.value == true) {
                    handler.post { userSearchAdapter.appendLoadingView() }
                } else {
                    userSearchAdapter.removeLoadingView()
                }
            })
        }
    }
}