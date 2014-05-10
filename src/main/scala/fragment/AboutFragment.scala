package com.github.ikuo.garaponoid

import android.os.Bundle
import android.os.Build.VERSION
import android.app.{Activity, Fragment}
import android.view.{View, ViewGroup, LayoutInflater}
import android.widget.TextView
import android.text.Html
import org.scaloid.common._
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
    implicit val view = inflater.inflate(R.layout.about_view, container, false)
    textView(TR.version_name).text(versionName)
    textView(TR.version_code).text(s"(#${versionCode})")
    textView(TR.license).setText(Html.fromHtml(getString(R.string.open_source_license)))
    view
  }

  private def textView(id: TypedResource[TextView])(implicit view: View) =
    view.findView[TextView](id)
}
