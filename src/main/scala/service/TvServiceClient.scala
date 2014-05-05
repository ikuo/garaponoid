package com.github.ikuo.garaponoid

import scala.concurrent._
import ExecutionContext.Implicits.global
import org.scaloid.common._
import com.github.ikuo.garapon4s._

trait TvServiceClient extends SActivity {
  protected val tvService = new LocalServiceConnection[TvService]

  protected def promptSignIn(userName: String) =
    new SignInDialog(userName, signIn, this).show

  private def signIn(loginId: String, md5Password: String): Unit = future {
    //TODO show a PopupWindow
    tvService.run { tv =>
      try {
        tv.updateAccount(loginId, md5Password)
        tv.refreshSession
      } catch {
        case e: UnknownUser => {
          new AlertDialogBuilder(null, R.string.unknown_user) {
            positiveButton(android.R.string.ok, promptSignIn(loginId))
          }.show
        }
        case e: WrongPassword => {
          new AlertDialogBuilder(null, R.string.wrong_password) {
            positiveButton(android.R.string.ok, promptSignIn(loginId))
          }.show
        }
      }
    }
  }
}
