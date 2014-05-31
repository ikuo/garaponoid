package com.github.ikuo.garaponoid

import android.app.SearchManager
import android.content.{Context, ComponentName}
import android.os.Bundle
import android.view.{Menu, MenuItem, Window}
import android.content.Intent
import org.scaloid.common._
import Tapper.Implicits._

class ProgramsActivity extends BaseActivity with ProgramsFragment.HostActivity {
  override def onCreate(state: Bundle): Unit = {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
    super.onCreate(state, Some(R.layout.fragment_container))
    spinnerVisible(false)
    if (state != null) { setIntent(null) }  // prevent duplicate query
  }

  override def onNewIntent(intent: Intent): Unit = {
    setIntent(intent)
    consumeIntent
  }

  override def onStart: Unit = {
    consumeIntent
    super.onStart
  }

  private def consumeIntent: Unit = {
    val intent = getIntent
    setIntent(null)
    if (intent == null) return ()
    if (intent.getAction == Intent.ACTION_SEARCH) {
      val query: Query =
        Option[Query](intent.getParcelableExtra(ProgramsFragment.query)) match {
          case None => new Query(key = intent.getStringExtra(SearchManager.QUERY))
          case Some(q) => q.asInstanceOf[Query]
        }
      val arguments = (new Bundle).tap(_.putParcelable("query", query))
      replaceFragment(new ScrollableProgramsFragment, Some(arguments)).commit
    }
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.programs_activity_actions, menu)
    activateProgramsSearchOnActionBar(menu)
    super.onCreateOptionsMenu(menu)
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
