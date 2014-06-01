package com.github.ikuo.garaponoid

import android.os.{Parcel, Parcelable}
import android.content.Context
import android.view.{View, ViewGroup}
import android.widget.TextView
import it.gmariotti.cardslib.library.internal.{Card, CardHeader}
import org.scaloid.common._
import Tapper.Implicits._

class ProgramCardParcelable(
  val title: String,
  val startDate: Long,
  val duration: String,
  val channel: String,
  val description: String,
  val thumbnailUrl: String,
  val webViewerUrl: String
) extends Parcelable {
  override def describeContents() = 0

  override def writeToParcel(out: Parcel, flags: Int): Unit = {
    out.writeString(title)
    out.writeLong(startDate)
    out.writeString(duration)
    out.writeString(channel)
    out.writeString(description)
    out.writeString(thumbnailUrl)
    out.writeString(webViewerUrl)
  }

  def decode(context: Context): ProgramCard =
    ProgramCard(context, title, startDate, duration, channel,
      description, thumbnailUrl, webViewerUrl)
}

object ProgramCardParcelable {
  val CREATOR = new Parcelable.Creator[ProgramCardParcelable] {
    def createFromParcel(in: Parcel): ProgramCardParcelable =
      new ProgramCardParcelable(
        in.readString, in.readLong,
        in.readString, in.readString, in.readString,
        in.readString, in.readString
      )

    def newArray(size: Int): Array[ProgramCardParcelable] =
      new Array[ProgramCardParcelable](size)
  }
}
