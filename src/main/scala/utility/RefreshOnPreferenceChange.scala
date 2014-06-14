package com.github.ikuo.garaponoid

import org.scaloid.common._

trait RefreshOnPreferenceChange extends BaseActivity {
  private var lastPrefs: Option[String] = None

  protected def refresh: Unit

  protected def isDirty: Boolean = (currentPrefs != lastPrefs)

  protected def refreshOnPreferenceChange: Unit = {
    if (isDirty) { refresh }
    updateLastPrefs
  }

  protected def updateLastPrefs: Unit = { this.lastPrefs = currentPrefs }

  private def currentPrefs: Option[String] =
    Option(defaultSharedPreferences.getString("pref_video_viewer", null))
}
