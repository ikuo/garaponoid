package com.github.ikuo.garaponoid

import android.app.Activity
import android.content.Intent
import android.os.{Bundle, Parcelable}
import android.view.{Menu, MenuItem, Window, View}
import android.widget.TextView
import TypedResource._
import org.scaloid.common._
import Tapper.Implicits._

class MainActivity
  extends BaseActivity
  with ProgramsFragment.HostActivity
  with RefreshOnPreferenceChange
{
  override def onCreate(state: Bundle): Unit = {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
    startService[TvService]

    getActionBar.show
    super.onCreate(state, Some(R.layout.main), false)

    spinnerVisible(false)

    showSignIn(state)
  }

  override def onDestroy: Unit = {
    stopService[TvService]
    super.onDestroy
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.main_activity_actions, menu)
    updateOptionsMenuVisibility(menu)
    activateProgramsSearchOnActionBar(menu)

    super.onCreateOptionsMenu(menu)
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    if (super.onOptionsItemSelected(item)) { return true }
    item.getItemId match {
      case R.id.action_refresh => refresh
      case R.id.action_sign_in => promptSignIn()
      case R.id.action_sign_out => signOut
      case _ => return false
    }
    true
  }

  override def onPrepareOptionsMenu(menu: Menu): Boolean = {
    updateOptionsMenuVisibility(menu)
    super.onPrepareOptionsMenu(menu)
  }

  override def onSeeMore: Unit = {
    val query: Parcelable = new Query
    startActivity(
      SIntent[ProgramsActivity](Intent.ACTION_SEARCH).
        tap(_.putExtra(ProgramsFragment.query, query))
    )
  }

  override def showMainPane(savedInstanceState: Bundle): Unit = runOnUiThread {
    findView(TR.sign_in_view).visibility(View.GONE)
    findView(TR.main_view).visibility(View.VISIBLE)
    if (savedInstanceState == null) { refresh }
    invalidateOptionsMenu
  }

  override def onStartQuery = spinnerVisible(true)

  override def onFinishQuery = runOnUiThread { spinnerVisible(false) }

  override def refresh: Unit = try {
    val arguments = (new Bundle).
      tap(_.putParcelable("query", new Query(perPage = Some(5))))

    replaceFragment(
      new FixedProgramsFragment,
      Some(arguments),
      R.id.fragment_container_new_programs
    ).commit
  } catch(handleError)

  private def updateOptionsMenuVisibility(menu: Menu): Unit = {
    tvService { tv =>
      menu.findItem(R.id.action_search).setVisible(tv.isSignedIn)
      menu.findItem(R.id.action_refresh).setVisible(tv.isSignedIn)
      menu.findItem(R.id.action_sign_out).setVisible(tv.isSignedIn)
      menu.findItem(R.id.action_sign_in).setVisible(!tv.isSignedIn)
    }
  }
}
