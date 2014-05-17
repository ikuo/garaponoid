package com.github.ikuo.garaponoid

import android.os.{Parcel, Parcelable}
import Query._

class Query(
  val key: String = null,
  val perPage: Option[Int] = None)
  extends Parcelable {
  override def describeContents: Int = 0

  override def writeToParcel(out: Parcel, flags: Int): Unit = {
    out.writeString(key)
    out.writeInt(serialize(perPage))
  }

  private def serialize(value: Option[Int]) = value match {
    case None => NA
    case Some(v) => v
  }

  override def toString = {
    s"key: ${key}, perPage: ${perPage}"
  }
}

object Query {
  val NA = -1
  val CREATOR = new Parcelable.Creator[Query] {
    override def createFromParcel(in: android.os.Parcel): Query = {
      new Query(
        in.readString,
        read(in.readInt)
      )
    }

    override def newArray(size: Int): Array[Query] = new Array[Query](size)

    private def read(value: Int) = value match {
      case NA => None
      case v => Some(v)
    }
  }
}
