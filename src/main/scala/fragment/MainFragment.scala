package com.github.ikuo.garaponoid

import android.os.Bundle
import android.view.{View, ViewGroup, LayoutInflater}

class MainFragment extends BaseFragment[TvServiceClient] {
  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View =
    inflater.inflate(R.layout.main_view, container, false)
}
