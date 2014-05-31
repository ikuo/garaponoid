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
import com.github.ikuo.garapon4s.TvSession
import com.github.ikuo.garapon4s.model.{Program, SearchResultListener}
import scala.collection.JavaConversions._
import Tapper.Implicits._
import ProgramsFragment._

trait ProgramsFragment extends BaseFragment[HostActivity] {
  implicit val loggerTag = LoggerTag("ProgramsFragment")

  val cardsName = "cards"
  protected var lastQuery: Option[Query] = None
  lazy val cards = new ArrayList[Card]()

  protected def addCard(card: Card)
  protected def context: Context = getActivity

  protected def tvService =
    getActivity.asInstanceOf[TvServiceClient].tvService

  override def onCreate(state: Bundle): Unit = {
    super.onCreate(state)
    cards
    if (state == null) {
      runQuery(getArguments.getParcelable("query").asInstanceOf[Query])
    } else {
      val cards = state.getParcelableArray(cardsName)
      info("read card-parcelables")
      info(cards.length.toString)
      //TODO
      runQuery(getArguments.getParcelable("query").asInstanceOf[Query])
    }
  }

  override def onSaveInstanceState(out: Bundle): Unit = {
    val array = new ArrayList[ProgramCardParcelable]()
    info(s"onSaveInstanceState ${cards.size}")
    for (card <- cards) {
      if (card.isInstanceOf[ProgramCard]) {
        array.append(card.asInstanceOf[ProgramCard].toParcelable)
      }
    }
    out.putParcelableArray(
      cardsName,
      array.toArray(new Array[Parcelable](array.length))
    )
    super.onSaveInstanceState(out)
  }

  private def addCard(program: Program, tvSession: TvSession): Unit =
    addCard(ProgramCard(
      getActivity,
      program.title,
      program.description,
      tvSession.thumbnailUrl(program.gtvId),
      tvSession.webViewerUrl(program.gtvId)
    ))

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

  protected def runQuery(query: Query): Unit = {
    implicit val ctx = getActivity
    this.lastQuery = Some(query)
    info(s"runQuery: ${query}")

    tvService.run { tv =>
      info("runQuery: service connected")
      if (tv.session.isEmpty) {
        info("runQuery: session is empty")
        hostActivity.refreshSession
        warn("Refreshed session")
        //runQuery(query) //TODO in callback
      } else {
        future {
          onStartQuery
          runQuery(query, tv.session.get)
        }.onComplete {
          case _ =>
            if (hostActivity != null) { onFinishQuery }
        }
      }
    }
  }

  protected def onStartQuery: Unit = {
    info("runQuery: startingQuery")
    hostActivity.onStartQuery
  }

  protected def onFinishQuery: Unit = hostActivity.onFinishQuery

  private def runQuery(query: Query, tvSession: TvSession): Unit = {
    Option[String](query.key).map(
      ProgramsSearchSuggestions.saveRecentQuery(getActivity, _))

    val results =
      tvSession.search(
        key = query.key,
        n = query.perPage.getOrElse(defaultPerPage),
        p = query.page,
        resultListener = searchResultListener(tvSession)
      )
    info(s"num results = ${results.hit}")
  }
}

object ProgramsFragment {
  val defaultPerPage = 30
  val query = "com.github.ikuo.garaponoid.programs.query"

  trait HostActivity extends TvServiceClient {
    def onStartQuery: Unit = ()
    def onFinishQuery: Unit = ()
    def onSeeMore: Unit = ()
  }
}
