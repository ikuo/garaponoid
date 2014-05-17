package com.github.ikuo.garaponoid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.{Menu, MenuItem, Window, View}
import android.widget.TextView
import TypedResource._
import org.scaloid.common._
import Tapper.Implicits._

class MainActivity extends BaseActivity with ProgramsFragment.HostActivity {
  override def onCreate(bundle: Bundle): Unit = {
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
    startService[TvService]

    getActionBar.show
    super.onCreate(bundle, Some(R.layout.main), false)

    refreshSessionOrPromptSignIn
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

  override def onOptionsItemSelected(item: MenuItem) = {
    item.getItemId match {
      case R.id.action_sign_in => promptSignIn()
      case R.id.action_sign_out => signOut
      case R.id.action_about => startActivity(SIntent[AboutActivity])
      case _ => ()
    }
    super.onOptionsItemSelected(item)
  }

  override def onPrepareOptionsMenu(menu: Menu): Boolean = {
    updateOptionsMenuVisibility(menu)
    super.onPrepareOptionsMenu(menu)
  }

  override def onSeeMore: Unit = {
    startActivity(
      SIntent[ProgramsActivity](Intent.ACTION_SEARCH).
        tap(_.putExtra(ProgramsFragment.query, new Query))
    )
  }

  override def showMainPane: Unit = runOnUiThread {
    findView(TR.sign_in_view).visibility(View.GONE)
    findView(TR.main_view).visibility(View.VISIBLE)

    val arguments = (new Bundle).
      tap(_.putParcelable("query", new Query(perPage = Some(5))))

    replaceFragment(
      new FixedProgramsFragment,
      Some(arguments),
      R.id.fragment_container_new_programs
    ).commit
  }

  override def onStartQuery = spinnerVisible(true)

  override def onFinishQuery = runOnUiThread {
    spinnerVisible(false)
    find[TextView](R.id.see_more).setVisibility(View.VISIBLE)
  }

  private def updateOptionsMenuVisibility(menu: Menu): Unit = {
    tvService { tv =>
      menu.findItem(R.id.action_sign_out).setVisible(tv.isSignedIn)
      menu.findItem(R.id.action_sign_in).setVisible(!tv.isSignedIn)
    }
  }
}
