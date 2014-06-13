package com.github.ikuo.garaponoid

import android.content.Context
import android.view.{View, ViewGroup}
import android.widget.TextView
import it.gmariotti.cardslib.library.internal.{
  Card, CardHeader, CardArrayAdapter, CardThumbnail
}
import android.text.format.DateUtils
import org.scaloid.common._
import Tapper.Implicits._
import TypedResource._

class ProgramCard private(
  context: Context,
  title: String,
  startDate: Long,
  duration: String,
  channel: String,
  description: String,
  thumbnailUrl: String,
  webViewerUrl: String
) extends Card(context, R.layout.program_card_inner_content)
{
  implicit val ctx: Context = context
  implicit val loggerTag = LoggerTag("ProgramCard")

  protected def setupInnerView: Unit = {
    this.addCardHeader(
      (new ProgramCardHeader(context, duration)).tap(_.setTitle(title))
    )
    this.addCardThumbnail(
      (new CardThumbnail(context)).
        tap(_.setUrlResource(thumbnailUrl))
    )

    this.setOnClickListener(new Card.OnCardClickListener {
      override def onClick(card: Card, view: View) =
        context.startActivity(
          SIntent[ProgramViewerActivity].
            tap(_.putExtra(ProgramViewerActivity.urlKey, webViewerUrl))
        )
    })
    this.setClickable(true)
  }

  override def setupInnerViewElements(parent: ViewGroup, view: View): Unit = {
    if (view == null) { warn("view is null"); return }

    view.findView(TR.program_description).text(description)
    view.findView(TR.program_channel).text(channel)
    view.findView(TR.program_datetime).text({
      import DateUtils._
      DateUtils.formatDateTime(context, startDate,
        FORMAT_SHOW_TIME|FORMAT_SHOW_DATE|FORMAT_SHOW_WEEKDAY|FORMAT_12HOUR)
    })

    super.setupInnerViewElements(parent, view)
  }

  def toParcelable =
    new ProgramCardParcelable(
      title, startDate, duration, channel, description,
      thumbnailUrl, webViewerUrl
    )
}

object ProgramCard {
  def apply(
    context: Context,
    title: String,
    startDate: Long,
    duration: String,
    channel: String,
    description: String,
    thumbnailUrl: String,
    webViewerUrl: String
  ): ProgramCard =
    new ProgramCard(
      context, title, startDate, duration, channel, description,
      thumbnailUrl, webViewerUrl
    ).tap(_.setupInnerView)
}
