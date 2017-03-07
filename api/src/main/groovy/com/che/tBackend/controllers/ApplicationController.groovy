package tBackend.controllers

import groovy.transform.InheritConstructors
import tBackend.models.User
import tBackend.lib.Encryptor

@InheritConstructors
class ApplicationController extends BaseController {
  static private Map<String, User> sessions = [:]

  def createTokenFor(User user) {
    def time = new Date()
    def token = Encryptor.encrypt("${user.email}@${time}")
    sessions[token] = user
    token
  }

  def removeCurrentToken() {
    sessions.remove currentToken
  }

  def isAuthorized() {
    !!currentUser()
  }

  def currentUser() {
    sessions[currentToken]
  }

  def getCurrentToken() {
    request.headers().get("Token")
  }
}
