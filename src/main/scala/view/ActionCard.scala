package com.github.ikuo.garaponoid

import android.content.Context
import android.view.{View, ViewGroup}
import android.widget.TextView
import it.gmariotti.cardslib.library.internal.Card
import org.scaloid.common._

class ActionCard(val context: Context, val label: String)
extends Card(context, R.layout.action_card_inner_content) {
  implicit val loggerTag = LoggerTag("ActionCard")

  override def setupInnerViewElements(parent: ViewGroup, view: View) {
    super.setupInnerViewElements(parent, view)
    if (view == null) { warn("view is null"); return }

    view.findViewById(R.id.action_label) match {
      case tv: TextView => tv.setText(label)
      case _ => warn("text view not found")
    }
  }
}
