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
  val lastQueryName = "lastQuery"
  protected var lastQuery: Option[Query] = None
  lazy val cards = new ArrayList[Card]()

  protected def addCard(card: Card)
  protected def context: Context = getActivity

  protected def tvService =
    if (getActivity == null) None
    else Some(getActivity.asInstanceOf[TvServiceClient].tvService)

  override def onCreate(state: Bundle): Unit = {
    super.onCreate(state)
    cards
    if (state != null) { return () }
    runQuery(getArguments.getParcelable("query").asInstanceOf[Query])
  }

  override def onActivityCreated(state: Bundle): Unit = {
    super.onActivityCreated(state)
    if (state == null) { return () }

    // restore cards
    { val parcels = state.getParcelableArray(cardsName)
      for (parcel <- parcels) parcel match {
        case p: ProgramCardParcelable => addCard(p.decode(getActivity))
        case _ => ()
      }
    }

    // restore last query
    val p: Parcelable = state.getParcelable(lastQueryName)
    if (p != null) {
      val q = p.asInstanceOf[Query]
      this.lastQuery = Some(q)
    }
  }

  override def onSaveInstanceState(out: Bundle): Unit = {
    // write cards
    { val array = new ArrayList[ProgramCardParcelable]()
      for (card <- cards) {
        if (card.isInstanceOf[ProgramCard]) {
          array.append(card.asInstanceOf[ProgramCard].toParcelable)
        }
      }
      out.putParcelableArray(cardsName,
        array.toArray(new Array[Parcelable](array.length)))
    }

    // write last query
    lastQuery.map { q: Query => out.putParcelable(lastQueryName, q) }

    super.onSaveInstanceState(out)
  }

  private def addCard(program: Program, tvSession: TvSession): Unit =
    if (getActivity == null) warn("Skipping addCard")
    else addCard(ProgramCard(
      getActivity,
      program.title,
      program.parsedStartDate.getTime,
      program.duration,
      program.bc,
      program.description,
      tvSession.thumbnailUrl(program.gtvId),
      selectViewerUrl(tvSession, program.gtvId)
    ))

  private def selectViewerUrl(tvSession: TvSession, gtvId: String): String = {
    implicit val ctx: Context = context.getApplicationContext
    defaultSharedPreferences.getString("pref_video_viewer", null) match {
      case "webviewer" => tvSession.webViewerUrl(gtvId)
      case "rtmp" => tvSession.rtmpUrl(gtvId)
      case _ => tvSession.streamingUrl(gtvId) // HLS
    }
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

  protected def runQuery(query: Query): Unit = {
    implicit val ctx = getActivity
    this.lastQuery = Some(query)

    tvService.map(_.run { tv =>
      info("runQuery: service connected")
      if (tv.session.isEmpty) {
        hostActivity.refreshSession({
          info("Retrying query after session refresh.")
          runQuery(query)
        })
      } else {
        future {
          onStartQuery
          runQuery(query, tv.session.get)
        }.map {
          case _ => if (hostActivity != null) { onFinishQuery }
        }.onFailure(handleError)
      }
    })
  }

  protected def onStartQuery: Unit = hostActivity.onStartQuery
  protected def onFinishQuery: Unit = hostActivity.onFinishQuery

  private def runQuery(query: Query, tvSession: TvSession): Unit = {
    Option[String](query.key).map(
      ProgramsSearchSuggestions.saveRecentQuery(getActivity, _))

    tvSession.search(
      key = query.key,
      n = query.perPage.getOrElse(defaultPerPage),
      p = query.page,
      resultListener = searchResultListener(tvSession)
    )
  }
}

object ProgramsFragment {
  val defaultPerPage = 30
  val query = "com.github.ikuo.garaponoid.programs.query"

  trait HostActivity extends TvServiceClient with ErrorHandling {
    def onStartQuery: Unit = ()
    def onFinishQuery: Unit = ()
    def onSeeMore: Unit = ()
  }
}
