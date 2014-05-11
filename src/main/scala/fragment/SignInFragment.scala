package com.github.ikuo.garaponoid

import android.os.Bundle
import android.view.{View, ViewGroup, LayoutInflater}
import org.scaloid.common._
import TypedResource._
import Tapper.Implicits._

class SignInFragment extends BaseFragment[TvServiceClient] {
  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View =
    inflater.inflate(R.layout.login_button_view, container, false).tap { view =>
      implicit val ctx = getActivity
      view.onClick(hostActivity.promptSignIn())
    }
}
