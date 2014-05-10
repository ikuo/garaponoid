package com.github.ikuo.garaponoid

import android.app.Activity
import android.os.Bundle
import android.view.{Menu, MenuItem}
import android.widget.SearchView
import TypedResource._
import org.scaloid.common._
import Tapper.Implicits._

class MainActivity extends BaseActivity with TvServiceClient {
  override def onCreate(bundle: Bundle): Unit = {
    getActionBar.show
    startService(TvService.intent)

    super.onCreate(bundle, Some(R.layout.main))

    tvService.run { tv =>
      if (tv.isSignedIn) refreshSession
      else promptSignIn("")
    }
  }

  override def onDestroy: Unit = {
    stopService(TvService.intent)
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.main_activity_actions, menu)

    menu.findItem(R.id.action_search).
      getActionView.asInstanceOf[SearchView].
      setSearchableInfo(ProgramsActivity.searchableInfo)

    super.onCreateOptionsMenu(menu)
  }

  override def onOptionsItemSelected(item: MenuItem) = {
    item.getItemId match {
      case R.id.action_sign_out => signOut
      case _ => ()
    }
    super.onOptionsItemSelected(item)
  }
}
