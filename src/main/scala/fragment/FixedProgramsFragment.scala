package com.github.ikuo.garaponoid

import android.os.{Parcelable, Bundle}
import android.view.{LayoutInflater, ViewGroup, View}
import org.scaloid.common._
import it.gmariotti.cardslib.library.view.CardView
import Tapper.Implicits._

class FixedProgramsFragment extends ProgramsFragment {
  val wc = ViewGroup.LayoutParams.WRAP_CONTENT

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View =
    inflater.inflate(R.layout.fixed_programs_view, container, false)

  override def addCard(card: ProgramCard): Unit = {
    val cardView = new CardView(getActivity).tap(_.setCard(card))
    val lp = new ViewGroup.LayoutParams(wc, wc)

    runOnUiThread {
      getView.asInstanceOf[ViewGroup].addView(cardView, lp)
    }
  }
}
