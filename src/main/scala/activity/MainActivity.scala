package com.github.ikuo.garaponoid

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import org.scaloid.common._
import Tapper.Implicits._

class MainActivity extends BaseActivity with TvServiceClient {
  private lazy val textView = findView(TR.textview)

  override def onCreate(bundle: Bundle) {
    setErrorHandler
    setContentView(R.layout.main)
    getActionBar.show
    super.onCreate(bundle)

    promptSignIn("")
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.main_activity_actions, menu)

    menu.findItem(R.id.action_search).
      getActionView.asInstanceOf[SearchView].
      setSearchableInfo(ProgramsActivity.searchableInfo)

    super.onCreateOptionsMenu(menu)
  }
}
