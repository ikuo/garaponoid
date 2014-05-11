package com.github.ikuo.garaponoid

import org.scaloid.common._

trait ErrorHandling {
  implicit val loggerTag: LoggerTag

  protected def setErrorHandler: Unit = {
    Thread.setDefaultUncaughtExceptionHandler(
      new Thread.UncaughtExceptionHandler {
        override def uncaughtException(thread: Thread, throwable: Throwable) {
          error(throwable.getMessage)
          error(throwable.getStackTrace.mkString("\n"))
        }
      }
    )
  }
}
