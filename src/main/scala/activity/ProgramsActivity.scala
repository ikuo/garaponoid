package com.github.ikuo.garaponoid

import android.app.SearchManager
import android.content.{Context, ComponentName}
import android.os.Bundle
import android.content.Intent
import org.scaloid.common._

class ProgramsActivity extends BaseActivity {
  override def onCreate(bundle: Bundle) {
    setErrorHandler
    super.onCreate(bundle)

    handleIntent(getIntent)
  }

  override def onNewIntent(intent: Intent) = handleIntent(intent)

  private def handleIntent(intent: Intent) {
    if (intent.getAction == Intent.ACTION_SEARCH) {
      val query = intent.getStringExtra(SearchManager.QUERY);
      info(query)
      //use the query to search your data somehow
      /*
      val results = tvSession.search(key = "News")
      val program = results.programs(0)
      val url = tvSession.webViewerUrl(program.gtvId)
      runOnUiThread(openUri(url))
      */
    }
  }
}

object ProgramsActivity {
  def componentName(implicit context: Context) =
    new ComponentName(context, classOf[ProgramsActivity])

  def searchableInfo(implicit context: Context) =
    searchManager.getSearchableInfo(componentName)
}
