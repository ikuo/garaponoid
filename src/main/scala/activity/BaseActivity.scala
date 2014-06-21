package com.github.ikuo.garaponoid

import org.scaloid.common._
import android.app.{Activity, Fragment, FragmentTransaction, DialogFragment}
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import android.view.Menu
import com.github.ikuo.android_error_dialog.ErrorDialogFragment
import TypedResource._
import Tapper.Implicits._

abstract class BaseActivity
  extends SActivity with TypedActivity
{
  private val dialogTag = "dialog"

  def onCreate(
    savedInstanceState: Bundle,
    layoutResourceId: Option[Int] = None,
    displayHomeAsUp: Boolean = true
  ): Unit = {
    super.onCreate(savedInstanceState)

    if (displayHomeAsUp) {
      getActionBar.setDisplayHomeAsUpEnabled(true)
    }
    layoutResourceId.map(id => setContentView(id))
  }

  def fatalError(ex: Throwable): Unit = showDialog(ErrorDialogFragment(ex))

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    super.onOptionsItemSelected(item)
    item.getItemId match {
      case android.R.id.home => finish
      case R.id.action_settings => startActivity(SIntent[SettingsActivity])
      case R.id.action_about => startActivity(SIntent[AboutActivity])
      case _ => return false
    }
    true
  }

  def showDialog(dialogFragment: => DialogFragment): Unit = {
    val fm = getFragmentManager
    val ft = fm.beginTransaction

    Option(fm.findFragmentByTag(dialogTag)).map { prev => ft.remove(prev) }

    dialogFragment.show(ft, dialogTag)
  }

  protected val handleError: PartialFunction[Throwable, Unit] = {
    case e: Throwable => fatalError(e)
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
