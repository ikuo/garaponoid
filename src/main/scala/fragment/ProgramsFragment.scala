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
import ProgramsFragment._

trait ProgramsFragment extends BaseFragment[HostActivity] {
  implicit val loggerTag = LoggerTag("ProgramsFragment")

  lazy val cards = new ArrayList[Card]()

  protected def addCard(card: ProgramCard)

  protected def context: Context = getActivity

  protected def tvService =
    getActivity.asInstanceOf[TvServiceClient].tvService

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    cards
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

    addCard(card)
  }

  private def searchResultListener(tvSession: TvSession) =
    new SearchResultListener {
      override def notifyPrograms(programs: Iterator[Program]): Unit = {
        while (programs.hasNext) {
          val program = programs.next
          debug(program.title)
          addCard(program, tvSession)
        }
      }
    }

  private def runQuery: Unit = {
    implicit val ctx = getActivity
    val query = getArguments.getParcelable("query").asInstanceOf[Query]
    info(s"runQuery: ${query}")

    tvService.run { tv =>
      if (tv.session.isEmpty) {
        hostActivity.refreshSession
      } else {
        future {
          hostActivity.onStartQuery
          runQuery(query, tv.session.get)
        }.onComplete {
          case _ =>
            if (hostActivity != null) { hostActivity.onFinishQuery }
        }
      }
    }
  }

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

object ProgramsFragment {
  val query = "com.github.ikuo.garaponoid.programs.query"

  trait HostActivity extends TvServiceClient {
    def onStartQuery: Unit = ()
    def onFinishQuery: Unit = ()
    def onSeeMore: Unit = ()
  }
}
