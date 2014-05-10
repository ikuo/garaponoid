package com.github.ikuo.garaponoid

import android.os.Bundle

class AboutActivity extends BaseActivity {
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState, Some(R.layout.fragment_container))
    showFragment(new AboutFragment)
  }
}
