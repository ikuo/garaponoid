package com.github.ikuo.garaponoid

import org.scaloid.common._
import com.github.ikuo.garapon4s.{TvClient, TvSession}
import android.content.Context
import android.content.ComponentName
import android.os.IBinder

class TvService extends LocalService {
  implicit val tag = LoggerTag("TvService")
  lazy val prefs = defaultSharedPreferences
  lazy val tvClient = new TvClient(gtvDevId)
  lazy val gtvDevId: String =
    try {
      val cls = Class.forName(s"com.github.ikuo.garaponoid.endorsed.W${"x"*2}iI${14*5}k")
      cls.getDeclaredMethod("value").invoke(cls).toString
    } catch {
      case _: Throwable => {
        info("Reading dev_id from string resource")
        getString(R.string.gtv_dev_id)
      }
    }

  private var mTvSession: Option[TvSession] = None

  override def onCreate: Unit = {
    super.onCreate
  }

  def session: Option[TvSession] = mTvSession
  def loginId = prefs.getString("loginId", null)
  def md5Password = prefs.getString("md5Password", null)

  def updateAccount(loginId: String, md5Password: String): Unit = {
    val editor = defaultSharedPreferences.edit
    editor.putString("loginId", loginId)
    editor.putString("md5Password", md5Password)
    editor.commit
  }

  def signOut: Unit = {
    val editor = defaultSharedPreferences.edit
    editor.putString("loginId", null)
    editor.putString("md5Password", null)
    editor.commit
  }

  def refreshSession: Unit = {
    this.mTvSession = Some(tvClient.newSession(loginId, md5Password))
  }

  def isSignedIn =
    defaultSharedPreferences.getString("loginId", null) != null
}
