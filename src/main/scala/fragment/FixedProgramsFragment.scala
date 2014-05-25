package com.github.ikuo.garaponoid

import android.os.{Parcelable, Bundle}
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import org.scaloid.common._
import it.gmariotti.cardslib.library.view.CardView
import it.gmariotti.cardslib.library.internal.Card
import Tapper.Implicits._

class FixedProgramsFragment extends ProgramsFragment {
  val wc = ViewGroup.LayoutParams.WRAP_CONTENT

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View =
    inflater.inflate(R.layout.fixed_programs_view, container, false)

  override def onActivityCreated(bundle: Bundle): Unit = {
    val actionCard =
      new ActionCard(getActivity, getString(R.string.see_more)).tap {
        _.setOnClickListener(new Card.OnCardClickListener {
          override def onClick(card: Card, view: View) = hostActivity.onSeeMore
        })
      }
    addCard(actionCard, 0)
    super.onActivityCreated(bundle)
  }

  override def addCard(card: Card): Unit = addCard(card, -1)

  private def addCard(card: Card, positionOffset: Int): Unit = {
    val inflater = getActivity.getLayoutInflater
    val layoutParams = new ViewGroup.LayoutParams(wc, wc)
    val parent = getView.asInstanceOf[ViewGroup]

    runOnUiThread {
      val position = parent.getChildCount + positionOffset
      val cardView = inflater.inflate(R.layout.fixed_program_card, parent, false).
        asInstanceOf[CardView].
        tap(_.setCard(card))
      parent.addView(cardView, position, layoutParams)
    }
  }
}
