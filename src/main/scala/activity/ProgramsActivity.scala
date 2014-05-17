package com.github.ikuo.garaponoid

import android.app.SearchManager
import android.content.{Context, ComponentName}
import android.os.Bundle
import android.view.{Menu, MenuItem, Window}
import android.content.Intent
import org.scaloid.common._
import Tapper.Implicits._

class ProgramsActivity extends BaseActivity with ProgramsFragment.HostActivity {
  override def onCreate(savedInstanceState: Bundle): Unit = {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
    super.onCreate(savedInstanceState, Some(R.layout.fragment_container))
    handleIntent(getIntent)
  }

  override def onNewIntent(intent: Intent): Unit =
    handleIntent(intent)

  private def handleIntent(intent: Intent): Unit = {
    if (intent == null) return ()
    if (intent.getAction == Intent.ACTION_SEARCH) {
      val query: Query =
        Option[Query](intent.getParcelableExtra(ProgramsFragment.query)) match {
          case None => new Query(key = intent.getStringExtra(SearchManager.QUERY))
          case Some(q) => q.asInstanceOf[Query]
        }
      val arguments = (new Bundle).tap(_.putParcelable("query", query))
      showFragment(new ScrollableProgramsFragment, Some(arguments)).commit
    }
    setIntent(null)
  }

  override def onStartQuery = spinnerVisible(true)
  override def onFinishQuery = spinnerVisible(false)
}

object ProgramsActivity {
  def componentName(implicit context: Context) =
    new ComponentName(context, classOf[ProgramsActivity])

  def searchableInfo(implicit context: Context) =
    searchManager.getSearchableInfo(componentName)
}
