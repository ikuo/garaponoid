package com.github.ikuo.garaponoid

import org.scaloid.common._
import android.app.{Activity, Fragment}
import android.os.Bundle
import android.view.MenuItem
import TypedResource._
import Tapper.Implicits._

abstract class BaseActivity extends SActivity with TypedActivity {
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

  protected def setErrorHandler: Unit = {
    Thread.setDefaultUncaughtExceptionHandler(
      new Thread.UncaughtExceptionHandler {
        override def uncaughtException(thread: Thread, throwable: Throwable) {
          error(throwable.getMessage)
          error(throwable.getStackTrace.mkString("\n"))
        }
      }
    )
  }

  protected def showFragment[T <: Fragment](
    fragment: T,
    arguments: Option[Bundle] = None,
    containerResourceId: Int = R.id.fragment_container
  ): T = {
    arguments.map(a => fragment.setArguments(a))
    getFragmentManager.beginTransaction.
      add(containerResourceId, fragment).
      commit
    fragment
  }
}
