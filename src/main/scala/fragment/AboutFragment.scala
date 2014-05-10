package com.github.ikuo.garaponoid

import android.os.Bundle
import android.os.Build.VERSION
import android.app.{Activity, Fragment}
import android.view.{View, ViewGroup, LayoutInflater}
import android.widget.TextView
import TypedResource._

class AboutFragment extends Fragment {
  lazy val versionName = packageInfo.versionName
  lazy val versionCode = packageInfo.versionCode
  lazy val packageInfo = {
    val context = getActivity
    context.getPackageManager.
      getPackageInfo(context.getPackageName, 0)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View = {
    val view = inflater.inflate(R.layout.about_view, container, false)
    view.findView[TextView](TR.version_name).setText(versionName)
    view.findView[TextView](TR.version_code).setText(s"(#${versionCode})")
    view
  }
}
