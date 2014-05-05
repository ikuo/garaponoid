package com.github.ikuo.garaponoid

class Tapper[T <: AnyRef](obj: T) {
  def tap(f: T => Any): T = {
    Option(obj).foreach(f)
    obj
  }
}

object Tapper {
  object Implicits {
    implicit def anyRef2Tapper[T <: AnyRef](obj: T) = new Tapper(obj)
  }
}
