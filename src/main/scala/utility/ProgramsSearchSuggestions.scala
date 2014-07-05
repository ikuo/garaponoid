package com.github.ikuo.garaponoid

import android.content.Context
import android.content.SearchRecentSuggestionsProvider
import android.provider.SearchRecentSuggestions
import SearchRecentSuggestionsProvider._
import ProgramsSearchSuggestions._

class ProgramsSearchSuggestions
  extends SearchRecentSuggestionsProvider {
    setupSuggestions(authorityName, DATABASE_MODE_QUERIES)
  }

object ProgramsSearchSuggestions {
  val authorityName = "com.github.ikuo.garaponoid"

  def saveRecentQuery(context: Context, query: String): Unit =
    if (context != null && query != null) {
      new SearchRecentSuggestions(context, authorityName, DATABASE_MODE_QUERIES).
        saveRecentQuery(query, null)
    }
}
