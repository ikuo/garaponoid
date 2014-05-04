package com.github.ikuo.garaponoid

import _root_.android.app.Activity
import _root_.android.os.Bundle
import android.content.Intent
import android.net.Uri

class MainActivity extends Activity with TypedActivity { activity =>
  // Set your dev_id, user_name, and md5password
  private lazy val client = new com.github.ikuo.garapon4s.TvClient("my_dev_id")
  private lazy val session =
    client.newSession("my_user_name", "my_md5password")

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    findView(TR.textview).setText("Loading ...")
    thread.start
  }

  private val thread: Thread = new Thread(
    new Runnable {
      override def run {
        activity.openUrl(sampleUrlOfWebViewer)
      }
    }
  )

  private lazy val sampleUrlOfWebViewer = {
    val results = session.search(key = "News")
    val program = results.programs(0)
    session.webViewerUrl(program.gtvId)
  }

  private def openUrl(url: String) =
    runOnUiThread(new Runnable {
      override def run {
        findView(TR.textview).setText(url)
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)))
      }
    })
}
