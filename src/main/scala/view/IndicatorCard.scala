package com.github.ikuo.garaponoid

import android.content.Context
import android.view.{View, ViewGroup}
import android.widget.TextView
import it.gmariotti.cardslib.library.internal.Card
import org.scaloid.common._

class IndicatorCard(val context: Context)
extends Card(context, R.layout.indicator_card_inner_content)
