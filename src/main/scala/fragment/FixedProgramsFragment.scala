package com.github.ikuo.garaponoid

import android.os.{Parcelable, Bundle}
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
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
    inflater.inflate(R.layout.fixed_programs_view, container, false).tap {
      implicit val ctx = getActivity
      _.find[TextView](R.id.see_more).onClick(hostActivity.onSeeMore)
    }

  override def addCard(card: ProgramCard): Unit = {
    val inflater = getActivity.getLayoutInflater
    val layoutParams = new ViewGroup.LayoutParams(wc, wc)
    val parent = getView.asInstanceOf[ViewGroup]

    runOnUiThread {
      val position = parent.getChildCount - 1
      val cardView = inflater.inflate(R.layout.fixed_program_card, parent, false).
        asInstanceOf[CardView].
        tap(_.setCard(card))
      parent.addView(cardView, position, layoutParams)
    }
  }
}
