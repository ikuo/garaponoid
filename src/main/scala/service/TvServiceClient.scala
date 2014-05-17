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

  protected def refreshSessionOrPromptSignIn: Unit = tvService.run { tv =>
    if (tv.isSignedIn) {
      refreshSession
    } else {
      showSignInPane
      promptSignIn()
    }
    findView(TR.button_sign_in).onClick(promptSignIn())
  }

  private def showSignInPane: Unit = runOnUiThread {
    findView(TR.main_view).setVisibility(View.GONE)
    findView(TR.sign_in_view).setVisibility(View.VISIBLE)
  }

  protected def showMainPane: Unit = ()

  private def signIn(loginId: String, md5Password: String): Unit =
    tvService.run { tv =>
      tv.updateAccount(loginId, md5Password)
      refreshSession
    }

  //TODO show a PopupWindow with indicator
  protected def refreshSession = tvService.run { tv =>
    info("refreshSession")
    future {
      try {
        tv.refreshSession
        showMainPane  // new programs etc.
      }
      catch {
        case e: UnknownUser => {
          new AlertDialogBuilder(null, R.string.unknown_user) {
            positiveButton(android.R.string.ok, promptSignIn(tv.loginId))
          }.show
        }
        case e: WrongPassword => {
          new AlertDialogBuilder(null, R.string.wrong_password) {
            positiveButton(android.R.string.ok, promptSignIn(tv.loginId))
          }.show
        }
      }
    }
  }
}
