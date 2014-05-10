package com.github.ikuo.garaponoid

import org.scaloid.common._
import android.content.Context
import java.security.MessageDigest
import java.math.BigInteger

class SignInDialog(
  defaultLoginId: String = "",
  signIn: (String, String) => Unit,
  implicit val activity: SActivity
) {

  private lazy val loginIdText = new SEditText(defaultLoginId)
  private lazy val passwordText = new SEditText()
  private lazy val loginId = loginIdText.text.toString
  private lazy val md5Password = md5sum(passwordText.text.toString)

  def show {
    new AlertDialogBuilder(R.string.garapon_tv_account) {
      positiveButton(R.string.sign_in, onSignIn)
      negativeButton(android.R.string.cancel)
      setView(signInView)
    }.show
  }

  private def onSignIn = signIn(loginId, md5Password)

  private lazy val signInView =
    new SVerticalLayout {
      STextView("ID")
      this += loginIdText //marginBottom 10.dip
      STextView("Password")
      this += passwordText inputType TEXT_PASSWORD
    }.padding(20.dip)

  private def md5sum(text: String) = {
    val bytes = MessageDigest.getInstance("MD5").digest(text.getBytes())
	  val hexString = (new BigInteger(1, bytes)).toString(16)
    ("0" * (32 - hexString.size)) + hexString // pad "0" chars to left
  }
}
