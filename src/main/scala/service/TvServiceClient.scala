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

  //TODO show a PopupWindow with indicator
  def refreshSession(onComplete: => Unit) = tvService.run { tv =>
    info("Refreshing session...")
    future {
      tv.refreshSession
      showMainPane(null)  // MainActivity
      onComplete
    }.onFailure {
      case _: UnknownUser => promptRetryLogin(R.string.unknown_user, tv.loginId)
      case _: WrongPassword => promptRetryLogin(R.string.wrong_password, tv.loginId)
      case e: Throwable => fatalError(e)
    }
  }

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

  protected def showMainPane(savedInstanceState: Bundle): Unit =
    invalidateOptionsMenu

  private def showSignInPane: Unit = runOnUiThread {
    findView(TR.main_view).setVisibility(View.GONE)
    findView(TR.sign_in_view).setVisibility(View.VISIBLE)
    invalidateOptionsMenu
  }

  private def signIn(loginId: String, md5Password: String): Unit =
    withIndicator {
      tvService.run { tv =>
        tv.updateAccount(loginId, md5Password)
        refreshSession()
      }
    }

  private def promptRetryLogin(messageId: Int, loginId: String): Unit =
    new AlertDialogBuilder(null, messageId) {
      positiveButton(android.R.string.ok, promptSignIn(loginId))
    }.show
}
