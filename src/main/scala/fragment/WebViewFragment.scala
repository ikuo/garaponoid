package com.github.ikuo.garaponoid

import android.app.Activity
import android.os.Bundle
import android.view.{View, ViewGroup, LayoutInflater}
import android.webkit.WebView
import org.scaloid.common._

class WebViewFragment extends BaseFragment[Activity with ErrorHandling] {
  implicit val loggerTag = LoggerTag("WebViewFragment")

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View = {
    val view = new WebView(getActivity)
    Option(getArguments.getString("url")).map { url => view.loadUrl(url) }
    view
  }
}
