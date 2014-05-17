package com.github.ikuo.garaponoid

import org.scaloid.common._
import android.app.{Activity, Fragment, FragmentTransaction}
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import android.view.Menu
import TypedResource._
import Tapper.Implicits._

abstract class BaseActivity
  extends SActivity with TypedActivity with ErrorHandling {
  def onCreate(
    savedInstanceState: Bundle,
    layoutResourceId: Option[Int] = None,
    displayHomeAsUp: Boolean = true
  ): Unit = {
    setErrorHandler

    super.onCreate(savedInstanceState)

    if (displayHomeAsUp) {
      getActionBar.setDisplayHomeAsUpEnabled(true)
    }
    layoutResourceId.map(id => setContentView(id))
  }

  override def onOptionsItemSelected(item: MenuItem) = {
    item.getItemId match {
      case android.R.id.home => finish
      case _ => ()
    }
    super.onOptionsItemSelected(item)
  }

  protected def showFragment[T <: Fragment](
    fragment: T,
    arguments: Option[Bundle] = None,
    containerResourceId: Int = R.id.fragment_container
  ): FragmentTransaction =
    fragmentTransaction(fragment, arguments).
      add(containerResourceId, fragment)

  protected def replaceFragment[T <: Fragment](
    fragment: T,
    arguments: Option[Bundle] = None,
    containerResourceId: Int = R.id.fragment_container
  ): FragmentTransaction =
    fragmentTransaction(fragment, arguments).
      replace(containerResourceId, fragment)

  protected def fragmentTransaction[T <: Fragment](
    fragment: T,
    arguments: Option[Bundle] = None
  ): FragmentTransaction = {
    arguments.map(a => fragment.setArguments(a))
    getFragmentManager.beginTransaction
  }

  protected def spinnerVisible(value: Boolean) =
    runOnUiThread(setProgressBarIndeterminateVisibility(value))

  protected def activateProgramsSearchOnActionBar(menu: Menu): Unit = {
    menu.findItem(R.id.action_search).
      getActionView.asInstanceOf[SearchView].
      setSearchableInfo(ProgramsActivity.searchableInfo)
  }
}
