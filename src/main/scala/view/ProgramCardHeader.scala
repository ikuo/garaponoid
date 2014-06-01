package com.github.ikuo.garaponoid

import java.util.Formatter
import android.content.Context
import android.view.{View, ViewGroup}
import it.gmariotti.cardslib.library.internal.{Card, CardHeader}
import org.scaloid.common._
import TypedResource._

class ProgramCardHeader(context: Context, duration: String)
extends CardHeader(context, R.layout.program_card_inner_header) {
  override def setupInnerViewElements(parent: ViewGroup, view: View): Unit = {
    super.setupInnerViewElements(parent, view)
    view.findView(TR.program_duration).
      text(format(duration))
  }

  private def format(duration: String): String =
    duration.split(":") match {
      case Array("00", m, _) => s"${m.toInt}m"
      case Array(h, "00", _) => s"${h.toInt}h"
      case Array(h,    m, _) => s"${h.toInt}h ${m.toInt}m"
      case _ => "-"
    }
}
