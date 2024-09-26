package code.keyur.mylistdemo.utils

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 *  + What happens in this class?
=>


 */
abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
    abstract fun loadMoreItems()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            Log.d(
                PaginationScrollListener::class.java.simpleName,
                "onScrolled: kkkkkkkkkkkkk: visibleItemCount: $visibleItemCount -- firstVisibleItemPosition: $firstVisibleItemPosition -- totalItemCount: $totalItemCount"
            )
            if (firstVisibleItemPosition + visibleItemCount >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }

    }

}