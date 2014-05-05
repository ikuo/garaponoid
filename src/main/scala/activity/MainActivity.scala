package com.github.ikuo.garaponoid

import android.app.Activity
import android.content.ComponentName
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import scala.concurrent._
import ExecutionContext.Implicits.global
import org.scaloid.common._

class MainActivity extends BaseActivity {
  private lazy val textView = findView(TR.textview)

  override def onCreate(bundle: Bundle) {
    setErrorHandler
    setContentView(R.layout.main)
    getActionBar.show
    super.onCreate(bundle)

    promptSignIn
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.main_activity_actions, menu)

    menu.findItem(R.id.action_search).
      getActionView.asInstanceOf[SearchView].
      setSearchableInfo(ProgramsActivity.searchableInfo)

    super.onCreateOptionsMenu(menu)
  }

  private def promptSignIn = new SignInDialog(signIn, this).show

  // TODO Replace this skeleton code
  private def signIn(loginId: String, md5Password: String): Unit = future {
    runOnUiThread(textView.text(s"Sign in with (${loginId}, ${md5Password}) ..."))

    try {
      val tvClient = new com.github.ikuo.garapon4s.TvClient(getString(R.string.gtv_dev_id))
      val tvSession = tvClient.newSession(loginId, md5Password)
      val results = tvSession.search(key = "News")
      val program = results.programs(0)
      val url = tvSession.webViewerUrl(program.gtvId)
      runOnUiThread(openUri(url))
    } catch {
      case e: Throwable => {
        toast("Invalid username or password")
        error("Caught exception: " + e.getMessage)
        error(e.getStackTrace.mkString)
        promptSignIn
      }
    }
  }
}
