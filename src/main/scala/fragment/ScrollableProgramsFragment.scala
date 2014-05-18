package com.github.ikuo.garaponoid

import android.os.{Parcelable, Bundle}
import android.view.{LayoutInflater, ViewGroup, View}
import it.gmariotti.cardslib.library.internal.{
  Card, CardHeader, CardArrayAdapter, CardThumbnail
}
import it.gmariotti.cardslib.library.view.{CardListView}
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
  protected lazy val cardsAdapter = new CardArrayAdapter(context, cards)

  override def onCreate(savedInstanceState: Bundle): Unit = {
    cardsAdapter
    super.onCreate(savedInstanceState)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View =
    inflater.inflate(R.layout.programs_view, container, false).tap { v =>
      val list = v.findView(TR.program_cards)
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
