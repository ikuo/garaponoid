package com.github.ikuo.garaponoid

import android.os.Bundle
import org.scaloid.common._
import Tapper.Implicits._
import ProgramViewerActivity._

class ProgramViewerActivity
  extends BaseActivity
  with ProgramViewerFragment.HostActivity
  with TvServiceClient
{
  override def onCreate(savedInstanceState: Bundle): Unit =
    super.onCreate(savedInstanceState, Some(R.layout.fragment_container))

  override def onResume: Unit = {
    consumeIntent
    super.onResume
  }

  override def onLoginRequired(listener: AccountListener): Unit =
    tvService.run { tv =>
      listener.notifyAccount(tv.loginId, tv.md5Password)
    }

  private def consumeIntent: Unit = {
    Option(getIntent).map { intent =>
      Option(intent.getStringExtra(urlKey)).map { url =>
        val arguments = (new Bundle).tap(_.putString("url", url))
        showFragment(new ProgramViewerFragment, Some(arguments)).
          commit
      }
    }
    setIntent(null)
  }
}

object ProgramViewerActivity {
  val urlKey = "com.github.ikuo.garaponoid.program_view.url"
}
