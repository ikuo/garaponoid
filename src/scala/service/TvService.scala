package com.github.ikuo.garaponoid

import org.scaloid.common._
import com.github.ikuo.garapon4s.{TvClient, TvSession}
import android.content.Context
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder

class TvService extends LocalService {
  lazy val prefs = defaultSharedPreferences
  lazy val tvClient = new TvClient(getString(R.string.gtv_dev_id))
  private var mTvSession: Option[TvSession] = None

  def session: Option[TvSession] = mTvSession
  def loginId = prefs.getString("loginId", null)
  def md5Password = prefs.getString("md5Password", null)

  def updateAccount(loginId: String, md5Password: String) {
    val editor = defaultSharedPreferences.edit
    editor.putString("loginId", loginId)
    editor.putString("md5Password", md5Password)
    editor.commit
  }

  def signOut {
    val editor = defaultSharedPreferences.edit
    editor.putString("loginId", null)
    editor.putString("md5Password", null)
    editor.commit
  }

  def refreshSession {
    this.mTvSession = Some(tvClient.newSession(loginId, md5Password))
  }

  def isSignedIn = {
    defaultSharedPreferences.getString("loginId", null) != null
  }
}

object TvService {
  def intent(implicit context: Context) =
    new Intent(context, classOf[TvService])
}
