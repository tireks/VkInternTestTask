package com.tirexmurina.vkinterntask.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface OnLoadMoreListener {
    fun onLoadMore()
}

class RecyclerViewLoadMoreScroll(
    layoutManager: GridLayoutManager
) : RecyclerView.OnScrollListener() {

    private lateinit var onLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var layoutManager: RecyclerView.LayoutManager = layoutManager

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