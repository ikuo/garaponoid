package com.github.ikuo.garaponoid

import android.os.Bundle
import android.preference.PreferenceFragment

class SettingsFragment extends PreferenceFragment {
  override def onCreate(state: Bundle): Unit = {
    super.onCreate(state)
    addPreferencesFromResource(R.xml.preferences)
  }
}
