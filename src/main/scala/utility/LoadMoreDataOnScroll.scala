package com.github.ikuo.garaponoid

import android.view.View
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener
import android.widget.AbsListView.OnScrollListener._

import org.scaloid.common._
import TypedResource._

trait LoadMoreDataOnScroll extends OnScrollListener {
  private var isLoading = false
  private var currentPage = 1

  override def onScroll(
    view: AbsListView,
    firstVisibleItem: Int,
    visibleItemCount: Int,
    totalItemCount: Int
  ): Unit = ()

  override def onScrollStateChanged(view: AbsListView, scrollState: Int): Unit = {
    if (scrollState != SCROLL_STATE_IDLE) { return () }
    if (isLoading) { return () }

    val listView = getView.find[AbsListView](R.id.program_cards)
    if (listView.getLastVisiblePosition >= listView.getCount - 1) {
      this.currentPage += 1
      this.isLoading = true
      loadMoreDataOnScroll(currentPage)
    }
  }

  def getView: View
  def loadMoreDataOnScroll(currentPage: Int): Unit

  protected def setLoading(value: Boolean): Unit = {
    this.isLoading = value
  }
}
