package com.github.ikuo.garaponoid

import android.os.{Parcelable, Bundle}
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.AbsListView
import it.gmariotti.cardslib.library.view.CardListView
import it.gmariotti.cardslib.library.internal.{
  Card, CardHeader, CardThumbnail,
  CardArrayAdapter, CardGridArrayAdapter
}
import it.gmariotti.cardslib.library.internal.base.{BaseCardArrayAdapter}
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

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View =
    inflater.inflate(R.layout.programs_view, container, false).tap { v =>
      val list = v.find[AbsListView](R.id.program_cards)
      this.cardsAdapter =
        if (list.isInstanceOf[CardListView]) new CardArrayAdapter(context, cards)
        else new CardGridArrayAdapter(context, cards)
      list.setAdapter(cardsAdapter)
      list.setOnScrollListener(self)
      list.setFastScrollEnabled(true)
    }

  override def addCard(card: ProgramCard): Unit =
    runOnUiThread {
      cardsAdapter.add(card)
      cardsAdapter.notifyDataSetChanged
    }

  override def loadMoreDataOnScroll(currentPage: Int): Unit = {
    if (lastQuery.isEmpty) {
      warn("Last query is empty. Skipping loadMoreDataOnScroll.")
      return ()
    }

    runQuery(lastQuery.get.copy(page = currentPage))
    info("loadMoreDataOnScroll")
  }
}
