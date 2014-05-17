package com.github.ikuo.garaponoid

import android.os.Bundle
import org.scaloid.common._

/**
 * A history-less activity to make MainActivity singleTask.
 * See also: http://lilpickle3000.wordpress.com/2013/01/30/android-singletask-launchmode/
 */
class LaunchActivity extends BaseActivity {
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    startActivity(SIntent[MainActivity])
    finish
  }
}
