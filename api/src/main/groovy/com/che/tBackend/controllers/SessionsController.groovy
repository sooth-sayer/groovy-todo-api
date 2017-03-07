package tBackend.controllers

import groovy.transform.InheritConstructors
import tBackend.models.User
import tBackend.lib.Encryptor
import tBackend.controllers.traits.RespondWithJson

@InheritConstructors
class SessionsController extends ApplicationController implements RespondWithJson {
  def post() {
    def params = permit()
    def encryptedPassword = Encryptor.encrypt(params.password)
    def user = User.where("email = '${params.email}' and password_encrypted = '${encryptedPassword}'")[0] as User

    if (!user) {
      status(403)
      respondWith errors: {
        other message: "Wrong user login or password"
      }
    } else {
      def token = createTokenFor(user)
      respondWith token: token
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
