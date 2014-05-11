package com.github.ikuo.garaponoid

import android.app.SearchManager
import android.content.{Context, ComponentName}
import android.os.Bundle
import android.view.{Menu, MenuItem, Window}
import android.content.Intent
import org.scaloid.common._
import Tapper.Implicits._

class ProgramsActivity extends BaseActivity with TvServiceClient {
  val fragmentTag = "programs_fragment"

  override def onCreate(savedInstanceState: Bundle) {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
    super.onCreate(savedInstanceState, Some(R.layout.fragment_container))
  }

  override def onNewIntent(intent: Intent) = {
    handleIntent(intent)
  }

  private def handleIntent(intent: Intent) {
    if (intent.getAction == Intent.ACTION_SEARCH) {
      val query = intent.getStringExtra(SearchManager.QUERY);
      val arguments = (new Bundle).tap(_.putString("query", query))
      showFragment(new ProgramsFragment, Some(arguments)).commit
    }
  }
}

object ProgramsActivity {
  def componentName(implicit context: Context) =
    new ComponentName(context, classOf[ProgramsActivity])

  def searchableInfo(implicit context: Context) =
    searchManager.getSearchableInfo(componentName)
}
