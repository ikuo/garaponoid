package com.github.ikuo.garaponoid

import android.content.Context
import android.view.{View, ViewGroup}
import android.widget.TextView
import it.gmariotti.cardslib.library.internal.{Card, CardHeader}
import org.scaloid.common._
import Tapper.Implicits._

class ProgramCard(implicit context: Context)
extends Card(context, R.layout.program_card_inner_content) {
  implicit val loggerTag = LoggerTag("ProgramCard")
  private var mDescription: Option[String] = None

  def setDescription(v: String) = { this.mDescription = Some(v) }

  override def setupInnerViewElements(parent: ViewGroup, view: View) {
    super.setupInnerViewElements(parent, view)
    if (view == null) { warn("view is null"); return }

    view.findViewById(R.id.program_description) match {
      case tv: TextView => mDescription.map(d => tv.setText(d))
      case _ => warn("text view not found")
    }
  }
}
