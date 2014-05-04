package com.github.ikuo.garaponoid

import _root_.android.app.Activity
import _root_.android.os.Bundle
import scala.concurrent._
import ExecutionContext.Implicits.global
import org.scaloid.common._

class MainActivity extends SActivity with TypedActivity { activity =>
  private lazy val textView = findView(TR.textview)

  override def onCreate(bundle: Bundle) {
    setErrorHandler
    super.onCreate(bundle)
    setContentView(R.layout.main)
    promptSignIn
  }

  private def promptSignIn = new SignInDialog(signIn, this).show

  private def setErrorHandler {
    Thread.setDefaultUncaughtExceptionHandler(
      new Thread.UncaughtExceptionHandler {
        override def uncaughtException(thread: Thread, throwable: Throwable) {
          warn(throwable.getMessage)
          warn(throwable.getStackTrace.mkString)
        }
      }
    )
  }

  // TODO Replace this skeleton code
  private def signIn(loginId: String, md5Password: String): Unit = future {
    setErrorHandler
    runOnUiThread(textView.text(s"Sign in with (${loginId}, ${md5Password}) ..."))

    try {
      val tvClient = new com.github.ikuo.garapon4s.TvClient(getString(R.string.gtv_dev_id))
      val tvSession = tvClient.newSession(loginId, md5Password)
      val results = tvSession.search(key = "News")
      val program = results.programs(0)
      val url = tvSession.webViewerUrl(program.gtvId)
      runOnUiThread(openUri(url))
    } catch {
      case e: Throwable => {
        toast("Invalid username or password")
        warn("Caught exception: " + e.getMessage)
        warn(e.getStackTrace.mkString)
        promptSignIn
      }
    }
  }
}
