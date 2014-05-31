package com.github.ikuo.garaponoid

import scala.concurrent._
import android.os.Bundle
import android.view.View
import ExecutionContext.Implicits.global
import org.scaloid.common._
import com.github.ikuo.garapon4s._
import Tapper.Implicits._

trait TvServiceClient extends BaseActivity {
  val tvService = new LocalServiceConnection[TvService]

  def promptSignIn(userName: String = "") =
    new SignInDialog(userName, signIn, this).show

  protected def signOut: Unit = {
    new AlertDialogBuilder(null, R.string.confirm_sign_out) {
      positiveButton(android.R.string.ok, {
        tvService.run(_.signOut)
        showSignInPane
      })
      negativeButton(android.R.string.cancel)
    }.show
  }

  protected def showSignIn(savedInstanceState: Bundle): Unit = tvService.run { tv =>
    if (tv.isSignedIn) {
      val mainView = findView(TR.main_view)
      if (mainView.getVisibility != View.VISIBLE) {
        showMainPane(savedInstanceState)
      }
    } else showSignInPane

    findView(TR.button_sign_in).onClick(promptSignIn())
  }

  private def showSignInPane: Unit = runOnUiThread {
    findView(TR.main_view).setVisibility(View.GONE)
    findView(TR.sign_in_view).setVisibility(View.VISIBLE)
  }

  protected def showMainPane(savedInstanceState: Bundle): Unit = ()

  private def signIn(loginId: String, md5Password: String): Unit =
    tvService.run { tv =>
      tv.updateAccount(loginId, md5Password)
      refreshSession()
    }

  //TODO show a PopupWindow with indicator
  def refreshSession(onComplete: => Unit) = tvService.run { tv =>
    info("Refreshing session...")
    future {
      try {
        tv.refreshSession
        showMainPane(null)  // MainActivity
        onComplete
      } catch {
        case e: UnknownUser =>
          new AlertDialogBuilder(null, R.string.unknown_user) {
            positiveButton(android.R.string.ok, promptSignIn(tv.loginId))
          }.show
        case e: WrongPassword =>
          new AlertDialogBuilder(null, R.string.wrong_password) {
            positiveButton(android.R.string.ok, promptSignIn(tv.loginId))
          }.show
      }
    }
  }
}
