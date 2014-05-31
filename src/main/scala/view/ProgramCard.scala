package com.github.ikuo.garaponoid

import android.content.Context
import android.view.{View, ViewGroup}
import android.widget.TextView
import it.gmariotti.cardslib.library.internal.{
  Card, CardHeader, CardArrayAdapter, CardThumbnail
}
import org.scaloid.common._
import Tapper.Implicits._

class ProgramCard private(
  context: Context,
  title: String,
  description: String,
  thumbnailUrl: String,
  webViewerUrl: String
) extends Card(context, R.layout.program_card_inner_content)
{
  implicit val ctx: Context = context
  implicit val loggerTag = LoggerTag("ProgramCard")

  protected def setupInnerView: Unit = {
    this.addCardHeader(
      (new ProgramCardHeader).tap(_.setTitle(title))
    )
    this.addCardThumbnail(
      (new CardThumbnail(context)).
        tap(_.setUrlResource(thumbnailUrl))
    )

    this.setOnClickListener(new Card.OnCardClickListener {
      override def onClick(card: Card, view: View) =
        openUri(webViewerUrl)
    })
    this.setClickable(true)
  }

  override def setupInnerViewElements(parent: ViewGroup, view: View) {
    if (view == null) { warn("view is null"); return }

    view.findViewById(R.id.program_description) match {
      case tv: TextView => tv.setText(description)
      case _ => warn("text view not found")
    }
    super.setupInnerViewElements(parent, view)
  }

  def toParcelable =
    new ProgramCardParcelable(
      title, description,
      thumbnailUrl, webViewerUrl
    )
}

object ProgramCard {
  def apply(
    context: Context,
    title: String,
    description: String,
    thumbnailUrl: String,
    webViewerUrl: String
  ): ProgramCard = {
    val card = new ProgramCard(
      context, title, description,
      thumbnailUrl, webViewerUrl
    )
    card.setupInnerView
    card
  }
}
