package com.github.ikuo.garaponoid

import java.util.ArrayList
import android.app.{Activity, Fragment}
import android.content.Context
import android.os.{Parcelable, Bundle}
import android.view.{LayoutInflater, ViewGroup, View}
import android.text.TextUtils._
import scala.concurrent._
import org.scaloid.common._
import ExecutionContext.Implicits.global
import it.gmariotti.cardslib.library.internal.{
  Card, CardHeader, CardArrayAdapter, CardThumbnail
}
import it.gmariotti.cardslib.library.view.{CardListView}
import com.github.ikuo.garapon4s.TvSession
import com.github.ikuo.garapon4s.model.{Program, SearchResultListener}
import Tapper.Implicits._

class ProgramsFragment extends BaseFragment[TvServiceClient] {
  implicit val loggerTag = LoggerTag("ProgramsFragment")

  lazy val cards = new ArrayList[Card]()
  lazy val cardsAdapter = new CardArrayAdapter(context, cards)

  private def context: Context = getActivity
  private def tvService =
    getActivity.asInstanceOf[TvServiceClient].tvService

  private def searchResultListener(tvSession: TvSession) =
    new SearchResultListener {
      override def notifyPrograms(programs: Iterator[Program]): Unit = {
        while (programs.hasNext) {
          val program = programs.next
          info(program.title)
          addCard(program, tvSession)
        }
      }
    }

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    cards
    cardsAdapter
    runQuery
  }

  private def addCard(program: Program, tvSession: TvSession): Unit = {
    implicit val context: Context = getActivity
    val card = new ProgramCard

    card.addCardHeader(
      (new ProgramCardHeader).tap(_.setTitle(program.title))
    )

    card.setDescription(program.description)

    card.addCardThumbnail(
      (new CardThumbnail(context)).
        tap(_.setUrlResource(tvSession.thumbnailUrl(program.gtvId)))
    )

    card.setOnClickListener(new Card.OnCardClickListener {
      override def onClick(card: Card, view: View) =
        openUri(tvSession.webViewerUrl(program.gtvId))
    })

    runOnUiThread(cardsAdapter.add(card))
    runOnUiThread(cardsAdapter.notifyDataSetChanged)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View = {
    val view = inflater.inflate(R.layout.programs_view, container, false)
    view.findViewById(R.id.program_cards).asInstanceOf[CardListView].
      setAdapter(cardsAdapter)
    view
  }

  private def runQuery: Unit = {
    implicit val ctx = getActivity
    val query = getArguments.getParcelable("query").asInstanceOf[Query]
    info(s"runQuery: ${query}")

    tvService.run { tv =>
      if (tv.session.isEmpty) {
        warn("no session") // TODO refresh session
      } else {
        future {
          spinnerVisible(true)
          runQuery(query, tv.session.get)
        }.onComplete {
          case _ => spinnerVisible(false)
        }
      }
    }
  }

  private def spinnerVisible(value: Boolean) =
    runOnUiThread(getActivity.setProgressBarIndeterminateVisibility(value))

  private def runQuery(query: Query, tvSession: TvSession): Unit = {
    val results =
      tvSession.search(
        key = query.key,
        n = query.perPage.getOrElse(40),
        resultListener = searchResultListener(tvSession)
      )
    info(s"num results = ${results.hit}")
  }
}
