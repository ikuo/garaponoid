package com.github.ikuo.garaponoid

import android.os.Bundle
import org.scaloid.common._

trait RefreshOnPreferenceChange extends BaseActivity {
  private var lastPrefs: Option[String] = None

  override def onCreate(
    state: Bundle,
    layoutResourceId: Option[Int] = None,
    displayHomeAsUp: Boolean = true
  ): Unit = {
    super.onCreate(state, layoutResourceId, displayHomeAsUp)
    updateLastPrefs
  }

  override def onResume: Unit = {
    super.onResume
    refreshOnPreferenceChange
  }

  protected def refresh: Unit

  private def refreshOnPreferenceChange: Unit = {
    if (isDirty) { refresh }
    updateLastPrefs
  }

  private def updateLastPrefs: Unit = { this.lastPrefs = currentPrefs }

  private def isDirty: Boolean = (currentPrefs != lastPrefs)

  private def currentPrefs: Option[String] =
    Option(defaultSharedPreferences.getString("pref_video_viewer", null))
}
