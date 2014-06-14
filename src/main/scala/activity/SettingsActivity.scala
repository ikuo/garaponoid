package com.github.ikuo.garaponoid

import android.app.Activity
import android.os.Bundle
import org.scaloid.common._

class SettingsActivity extends BaseActivity {
  override def onCreate(state: Bundle): Unit = {
    super.onCreate(state, Some(R.layout.fragment_container))
    if (state == null) { replaceFragment(new SettingsFragment).commit }
  }
}
