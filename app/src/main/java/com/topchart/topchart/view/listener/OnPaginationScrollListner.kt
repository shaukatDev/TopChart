package com.topchart.topchart.view.listener

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class OnPaginationScrollListner(var layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {




    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val lastIndex = totalItemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreSongs(lastIndex)
            }
        }
    }

    protected abstract fun loadMoreSongs(fromIndex: Int)
    protected abstract fun isLastPage(): Boolean

}