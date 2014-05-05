package com.github.ikuo.garaponoid

import org.scaloid.common._
import com.github.ikuo.garapon4s.{TvClient, TvSession}

class TvService extends LocalService {
  lazy val prefs = defaultSharedPreferences
  lazy val tvClient = new TvClient(getString(R.string.gtv_dev_id))
  private var tvSession: Option[TvSession] = None

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
    this.tvSession = Some(tvClient.newSession(loginId, md5Password))
  }
}
