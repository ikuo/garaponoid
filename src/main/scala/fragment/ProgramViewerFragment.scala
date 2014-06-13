package com.github.ikuo.garaponoid

import android.app.Activity
import android.os.Bundle
import android.view.{View, ViewGroup, LayoutInflater}
import android.webkit.{WebView, WebViewClient, WebChromeClient, WebSettings}
import org.scaloid.common._
import Tapper.Implicits._
import ProgramViewerFragment._

class ProgramViewerFragment
  extends BaseFragment[HostActivity]
  with AccountListener
{ fragment =>
  implicit val loggerTag = LoggerTag("ProgramViewerFragment")
  private var webview: Option[WebView] = None

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View =
    new WebView(getActivity).tap { wv =>
      Option(getArguments.getString("url")).map { originalUrl =>
        wv.getSettings.tap { s =>
          s.setJavaScriptEnabled(true)
          s.setPluginState(WebSettings.PluginState.ON)
        }
        wv.setWebViewClient(webViewClient(originalUrl))
        wv.setWebChromeClient(webChromeClient)
        this.webview = Some(wv)

        wv.loadUrl(originalUrl)
      }
    }

  override def onDestroy: Unit = {
    this.webview.map { wv =>
      wv.stopLoading
      wv.setWebChromeClient(null)
      wv.setWebViewClient(null)
      wv.loadUrl("about:blank")
      wv.clearHistory
      wv.clearCache(true)
      wv.freeMemory
      wv.pauseTimers
    }
    this.webview = None
    super.onDestroy
  }

  override def notifyAccount(loginId: String, password: String): Unit = {
    if (loginId == null || password == null) { return }
    val webview = getView.asInstanceOf[WebView]
    evalJs(webview, "$('#loginid').val('" ++ loginId ++ "');")
    evalJs(webview, "$('#passwd').val('" ++ password ++ "');")
    evalJs(webview, """$('a:contains("ログイン")').click();""")
  }

  private def webViewClient(originalUrl: String) = new WebViewClient() {
    override def onPageFinished(view: WebView, url: String): Unit = {
      info(s"pageFinished: ${url}")
      evalJs(view, """$('a:contains("もう一度ログインし直す")').click();""");

      url match {
        case authUrl() => hostActivity.onLoginRequired(fragment)
        case topUrl() => view.loadUrl(originalUrl)
        case _ => warn(s"Unknown URL: ${url}")
      }
    }
  }

  private def webChromeClient = new WebChromeClient() {
    override def onProgressChanged(view: WebView, progress: Int): Unit =
      getActivity.setProgress(progress * 1000)
  }

  private def evalJs(webview: WebView, script: String): Unit =
    webview.loadUrl(s"javascript:${script};")
}

object ProgramViewerFragment {
  lazy val authUrl = """.+/auth/login.garapon$""".r
  lazy val topUrl = """.+/main.garapon$""".r

  trait HostActivity {
    def onLoginRequired(listener: AccountListener)
  }
}
