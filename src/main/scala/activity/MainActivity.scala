package com.github.ikuo.garaponoid

import android.app.Activity
import android.os.Bundle
import android.view.{Menu, MenuItem, Window}
import android.widget.SearchView
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

    menu.findItem(R.id.action_search).
      getActionView.asInstanceOf[SearchView].
      setSearchableInfo(ProgramsActivity.searchableInfo)

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

  private def updateOptionsMenuVisibility(menu: Menu): Unit = {
    tvService { tv =>
      menu.findItem(R.id.action_sign_out).setVisible(tv.isSignedIn)
      menu.findItem(R.id.action_sign_in).setVisible(!tv.isSignedIn)
    }
  }

  override def onStartQuery = spinnerVisible(true)
  override def onFinishQuery = spinnerVisible(false)
}
