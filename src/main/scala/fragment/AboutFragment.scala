package com.github.ikuo.garaponoid

import android.os.Bundle
import android.app.{Activity, Fragment}
import android.view.{View, ViewGroup, LayoutInflater}

class AboutFragment extends Fragment {
  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View = {
    inflater.inflate(R.layout.about_view, container, false)
  }
}
