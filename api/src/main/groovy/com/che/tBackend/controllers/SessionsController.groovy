package tBackend.controllers

import groovy.transform.InheritConstructors
import groovy.json.JsonBuilder
import tBackend.models.User
import tBackend.lib.Encryptor

@InheritConstructors
class SessionsController extends ApplicationController {
  def post() {
    def params = permit()
    def encryptedPassword = Encryptor.encrypt(params.password)
    def user = User.where("email = '${params.email}' and password_encrypted = '${encryptedPassword}'")[0] as User

    if (!user) {
      def json = new JsonBuilder()
      json.errors {
        other message: "Wrong user login or password"
      }

      status(403)
      respondWith json.toString()
    } else {
      def token = createTokenFor(user)
      def json = new JsonBuilder()
      json token: token
      respondWith json.toString()
    }
  }

  def delete() {
    if (!isAuthorized()) {
      status(403)
      return
    }

    removeCurrentToken()
  }

  def permit() {
    def body = body.asJson()
    def user = [:]

    ["email", "password"].each {
      user[it] = body[it]
    }

    user
  }
}
