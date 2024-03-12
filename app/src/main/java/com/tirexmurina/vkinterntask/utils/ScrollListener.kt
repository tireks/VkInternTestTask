package com.tirexmurina.vkinterntask.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

interface OnLoadMoreListener {
    fun onLoadMore()
}

class RecyclerViewLoadMoreScroll @Inject constructor(
) : RecyclerView.OnScrollListener() {

    private lateinit var onLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false

    fun setLoaded() {
        isLoading = false
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        this.onLoadMoreListener = listener
    }


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(1)) {
            if (!isLoading){
                isLoading = true
                onLoadMoreListener.onLoadMore()
            }
        }
    }
}