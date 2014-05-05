package com.github.ikuo.garaponoid

import android.app.SearchManager
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
    info("Got search intent")
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      val query = intent.getStringExtra(SearchManager.QUERY);
      //use the query to search your data somehow
    }
  }
}
