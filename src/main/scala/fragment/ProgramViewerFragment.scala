package com.github.ikuo.garaponoid

import android.app.Activity
import android.os.{Bundle, Build}
import android.view.{View, ViewGroup, LayoutInflater}
import android.webkit.{WebView, WebViewClient, WebChromeClient,
  WebSettings, WebResourceResponse}
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
      info(s"creating WebView")
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        WebView.setWebContentsDebuggingEnabled(true);
      }
      Option(getArguments.getString("url")).map { originalUrl =>
        info(s"originalUrl: ${originalUrl}")
        wv.getSettings.tap { s =>
          s.setJavaScriptEnabled(true)
          s.setPluginState(WebSettings.PluginState.ON)
          s.setSavePassword(false)
        }
        wv.setWebViewClient(webViewClient(originalUrl))
        wv.setWebChromeClient(webChromeClient)
        this.webview = Some(wv)

        wv.loadUrl(originalUrl)
      }
    }

  override def onDestroyView: Unit = {
    this.webview.map { wv =>
      info("Destroying webview")
      wv.pauseTimers
      wv.stopLoading
      wv.setWebChromeClient(null)
      wv.setWebViewClient(null)
      wv.loadUrl("about:blank")
      wv.clearHistory
      wv.clearCache(true)
      wv.removeAllViews
      wv.destroy
    }
    this.webview = None
    super.onDestroyView
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

    override def onLoadResource(view: WebView, url: String): Unit = {
      info(s"onLoadResource ${url}")
      if (url.contains("/swf/fp/swfobject.js")) {
        info("Going to fire workaround 1")
        evalJs(view, "alert('1')")
        //evalJs(view, "alert(swfobject)")
        evalJs(view, "$.ready()")
      }
      super.onLoadResource(view, url)
    }

    override def shouldOverrideUrlLoading(view: WebView, url: String): Boolean = {
      info(s"shouldOverrideUrlLoading ${url}")
      super.shouldOverrideUrlLoading(view, url)
    }

    override def shouldInterceptRequest(view: WebView, url: String): WebResourceResponse = {
      info(s"shouldInterceptRequest ${url}")
      super.shouldInterceptRequest(view, url)
    }

    override def onReceivedError(
      view: WebView,
      errorCode: Int,
      description: String,
      url: String
    ): Unit = error(s"${errorCode} ${url} ${description}")
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
