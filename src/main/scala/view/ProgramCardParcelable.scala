package com.github.ikuo.garaponoid

import android.os.{Parcel, Parcelable}
import android.view.{View, ViewGroup}
import android.widget.TextView
import it.gmariotti.cardslib.library.internal.{Card, CardHeader}
import org.scaloid.common._
import Tapper.Implicits._

class ProgramCardParcelable(
  val title: String,
  val description: String,
  val thumbnailUrl: String,
  val webViewerUrl: String
) extends Parcelable {
  override def describeContents() = 0

  override def writeToParcel(out: Parcel, flags: Int): Unit = {
    out.writeString(title)
    out.writeString(description)
    out.writeString(thumbnailUrl)
    out.writeString(webViewerUrl)
  }
}

object ProgramCardParcelable {
  val CREATOR = new Parcelable.Creator[ProgramCardParcelable] {
    def createFromParcel(in: Parcel): ProgramCardParcelable =
      new ProgramCardParcelable(
        in.readString, in.readString,
        in.readString, in.readString
      )

    def newArray(size: Int): Array[ProgramCardParcelable] =
      new Array[ProgramCardParcelable](size)
  }
}
