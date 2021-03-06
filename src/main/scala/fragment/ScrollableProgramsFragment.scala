package com.github.ikuo.garaponoid

import android.os.{Parcelable, Bundle}
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.AbsListView
import it.gmariotti.cardslib.library.view.CardListView
import it.gmariotti.cardslib.library.internal.{
  Card, CardHeader, CardThumbnail, CardGridArrayAdapter
}
import it.gmariotti.cardslib.library.internal.base.BaseCardArrayAdapter
import org.scaloid.common._
import TypedResource._
import Tapper.Implicits._

/**
 * Scrollable fragment of programs.
 * Do not place it under another scrollable view e.g. ScrollView.
 */
class ScrollableProgramsFragment
  extends ProgramsFragment
  with LoadMoreDataOnScroll
{ self =>
  protected var cardsAdapter: BaseCardArrayAdapter = null
  private var indicator: Option[IndicatorCard] = None

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View =
    inflater.inflate(R.layout.programs_view, container, false).tap { v =>
      val list = v.find[AbsListView](R.id.program_cards)
      this.cardsAdapter = new CardGridArrayAdapter(context, cards)
      list.setAdapter(cardsAdapter)
      list.setOnScrollListener(self)
      list.setFastScrollEnabled(true)
    }

  override protected def onStartQuery: Unit = try {
    val card = new IndicatorCard(getActivity)
    this.indicator = Some(card)
    runOnUiThread {
      try {
        cards.add(card)
        cardsAdapter.notifyDataSetChanged
      } catch handleError
    }
    if (currentPage > 1) { super.onStartQuery }
  } catch handleError

  override protected def onFinishQuery: Unit = try {
    if (cards.size <= 0) { return () }
    runOnUiThread {
      indicator.map { c => cards.remove(c) }
      cardsAdapter.notifyDataSetChanged
    }
    super.onFinishQuery
    setLoading(false)
  } catch handleError

  override def addCard(card: Card): Unit = runOnUiThread {
    try {
      cardsAdapter.add(card)
      cardsAdapter.notifyDataSetChanged
    } catch handleError
  }

  override def loadMoreDataOnScroll(currentPage: Int): Unit = try {
    if (lastQuery.isEmpty)
      warn("Last query is empty. Skipping loadMoreDataOnScroll.")
    else
      runQuery(lastQuery.get.copy(page = currentPage))
  } catch handleError

  override def onSaveInstanceState(out: Bundle): Unit = {
    super.onSaveInstanceState(out)
    savePageState(out)
  }

  override def onActivityCreated(state: Bundle): Unit = {
    super.onActivityCreated(state)
    restorePageState(state)
  }
}
