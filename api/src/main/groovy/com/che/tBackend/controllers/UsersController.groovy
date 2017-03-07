package tBackend.controllers

import groovy.transform.InheritConstructors
import tBackend.models.User
import tBackend.controllers.traits.RespondWithJson

@InheritConstructors
class UsersController extends ApplicationController implements RespondWithJson {
  def post() {
    try {
      def params = permit()
      def user = new User(params)

      if (!user.validate()) {
        status(422)
        respondWith errors: user.errors

        return
      }

      user.save()
      respondWith id: user.id, email: user.email

    } catch (groovy.json.JsonException e) {
      status(422)
      respondWith errors {
        other message: e.message
      }
    }
  }

  def permit() {
    def body = body.asJson()
    def user = [:]

    ["email", "password", "password_confirmation"].each {
      user[it] = body[it]
    }

    user
  }
}
