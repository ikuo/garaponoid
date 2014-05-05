package com.github.ikuo.garaponoid

import org.scaloid.common._

trait TvServiceClient extends SActivity {
  protected val tv = new LocalServiceConnection[TvService]
}
