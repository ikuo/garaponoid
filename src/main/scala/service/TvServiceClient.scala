package com.github.ikuo.garaponoid

import scala.concurrent._
import ExecutionContext.Implicits.global
import org.scaloid.common._
import com.github.ikuo.garapon4s._

trait TvServiceClient extends BaseActivity {
  val tvService = new LocalServiceConnection[TvService]

  def promptSignIn(userName: String = "") =
    new SignInDialog(userName, signIn, this).show

  protected def signOut: Unit = {
    new AlertDialogBuilder(null, R.string.confirm_sign_out) {
      positiveButton(android.R.string.ok, {
        tvService.run(_.signOut)
        showSignInFragment
      })
      negativeButton(android.R.string.cancel)
    }.show
  }

  protected def refreshSessionOrPromptSignIn: Unit = tvService.run { tv =>
    if (tv.isSignedIn) {
      refreshSession
    } else {
      showSignInFragment
      promptSignIn()
    }
  }

  private def showSignInFragment: Unit =
    showFragment(new SignInFragment).commit

  private def signIn(loginId: String, md5Password: String): Unit =
    tvService.run { tv =>
      tv.updateAccount(loginId, md5Password)
      refreshSession
    }

  //TODO show a PopupWindow with indicator
  protected def refreshSession = tvService.run { tv =>
    info("refreshSession")
    future {
      try { tv.refreshSession }
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
