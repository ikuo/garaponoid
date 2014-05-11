package com.github.ikuo.garaponoid

import scala.reflect.ClassTag
import android.app.{Activity, Fragment}

/**
 * A Fragment with runtime type check on hosting activity.
 */
abstract class BaseFragment[T](implicit activityClassTag: ClassTag[T])
  extends Fragment with ErrorHandling {
  protected def hostActivity = getActivity.asInstanceOf[T]

  override def onAttach(activity: Activity): Unit = {
    setErrorHandler
    val expectedClass = activityClassTag.runtimeClass
    val actualClass = activity.getClass
    if (!expectedClass.isAssignableFrom(actualClass)) {
      val msg = s"${actualClass.getName} must implements ${expectedClass.getName}"
      throw new ClassCastException(msg)
    }
    super.onAttach(activity)
  }
}
