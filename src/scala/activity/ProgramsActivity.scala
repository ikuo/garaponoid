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
    setErrorHandler
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
    super.onCreate(savedInstanceState)
    getActionBar.setDisplayHomeAsUpEnabled(true)
    setContentView(R.layout.programs)
  }

  override def onNewIntent(intent: Intent) = {
    handleIntent(intent)
  }

  private def handleIntent(intent: Intent) {
    if (intent.getAction == Intent.ACTION_SEARCH) {
      val query = intent.getStringExtra(SearchManager.QUERY);
      showFragment(query)
    }
  }

  override def onOptionsItemSelected(item: MenuItem) = {
    item.getItemId match {
      case android.R.id.home => finish
      case _ => ()
    }
    super.onOptionsItemSelected(item)
  }

  private def showFragment(query: String) {
    val arguments = (new Bundle).tap(_.putString("query", query))
    val fragment = (new ProgramsFragment).tap(_.setArguments(arguments))
    getFragmentManager.beginTransaction.
      add(R.id.fragment_container, fragment, fragmentTag).
      commit
  }
}

object ProgramsActivity {
  def componentName(implicit context: Context) =
    new ComponentName(context, classOf[ProgramsActivity])

  def searchableInfo(implicit context: Context) =
    searchManager.getSearchableInfo(componentName)
}
