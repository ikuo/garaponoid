package com.github.ikuo.garaponoid

import android.os.Bundle
import org.scaloid.common._
import Tapper.Implicits._

class AboutActivity extends BaseActivity with AboutFragment.HostActivity {
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState, Some(R.layout.fragment_container))
    if (savedInstanceState == null) {
      showFragment(new AboutFragment).commit
    }
  }

  override def showOpenSourceLicense: Unit = {
    val arguments =
      (new Bundle).
        tap(_.putString("url", "file:///android_res/raw/notice.html"))
    info("showOpenSourceLicense")
    showFragment(new WebViewFragment, Some(arguments)).
      addToBackStack(null).commit
  }
}
