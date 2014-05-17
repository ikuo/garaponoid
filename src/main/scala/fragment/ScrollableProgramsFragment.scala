package com.github.ikuo.garaponoid

import android.os.{Parcelable, Bundle}
import android.view.{LayoutInflater, ViewGroup, View}
import it.gmariotti.cardslib.library.internal.{
  Card, CardHeader, CardArrayAdapter, CardThumbnail
}
import it.gmariotti.cardslib.library.view.{CardListView}
import org.scaloid.common._

class ScrollableProgramsFragment extends ProgramsFragment {
  lazy val cardsAdapter = new CardArrayAdapter(context, cards)

  override def onCreate(savedInstanceState: Bundle): Unit = {
    cardsAdapter
    super.onCreate(savedInstanceState)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View = {
    val view = inflater.inflate(R.layout.programs_view, container, false)
    view.findViewById(R.id.program_cards).asInstanceOf[CardListView].
      setAdapter(cardsAdapter)
    view
  }

  override def addCard(card: ProgramCard): Unit = {
    runOnUiThread {
      cardsAdapter.add(card)
      cardsAdapter.notifyDataSetChanged
    }
  }
}
