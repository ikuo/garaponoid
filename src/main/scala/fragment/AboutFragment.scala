package com.github.ikuo.garaponoid

import android.os.Bundle
import android.os.Build.VERSION
import android.view.{View, ViewGroup, LayoutInflater}
import android.widget.TextView
import android.text.Html
import org.scaloid.common._
import TypedResource._
import AboutFragment._

class AboutFragment extends BaseFragment[HostActivity] {
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
    textView(TR.license).text(Html.fromHtml(getString(R.string.open_source_license)))
    textView(TR.license).onClick(hostActivity.showOpenSourceLicense)
    view
  }

  private def textView(id: TypedResource[TextView])(implicit view: View) =
    view.findView[TextView](id)
}

object AboutFragment {
  trait HostActivity {
    def showOpenSourceLicense: Unit
  }
}
