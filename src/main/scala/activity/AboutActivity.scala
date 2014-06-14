package com.github.ikuo.garaponoid

import android.os.Bundle
import org.scaloid.common._
import Tapper.Implicits._

class AboutActivity extends BaseActivity with AboutFragment.HostActivity {
  override def onCreate(state: Bundle): Unit = {
    super.onCreate(state, Some(R.layout.fragment_container))
    if (state == null) { showFragment(new AboutFragment).commit }
  }

  override def showOpenSourceLicense: Unit = {
    val arguments =
      (new Bundle).
        tap(_.putString("url", "file:///android_res/raw/notice.html"))
    showFragment(new WebViewFragment, Some(arguments)).
      addToBackStack(null).commit
  }
}
