package com.github.ikuo.garaponoid

trait AccountListener {
  def notifyAccount(loginId: String, password: String): Unit
}
