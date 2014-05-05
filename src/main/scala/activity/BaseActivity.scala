package com.github.ikuo.garaponoid

import org.scaloid.common._

abstract class BaseActivity
extends SActivity with TypedActivity
{
  protected def setErrorHandler {
    Thread.setDefaultUncaughtExceptionHandler(
      new Thread.UncaughtExceptionHandler {
        override def uncaughtException(thread: Thread, throwable: Throwable) {
          error(throwable.getMessage)
          error(throwable.getStackTrace.mkString)
        }
      }
    )
  }
}
