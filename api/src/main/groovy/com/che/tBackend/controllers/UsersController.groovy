package tBackend.controllers

import groovy.json.JsonBuilder
import groovy.transform.InheritConstructors
import tBackend.models.User

@InheritConstructors
class UsersController extends ApplicationController {
  def post() {
    try {
      def params = permit()
      def user = new User(params)

      if (!user.validate()) {
        def json = new JsonBuilder()
        json.errors user.errors
        status(422)
        respondWith json.toString()
        return
      }

      user.save()
      def json = new JsonBuilder()
      json id: user.id, email: user.email
      respondWith json.toString()

    } catch (groovy.json.JsonException e) {
      def json = new JsonBuilder()
      json.errors {
        other message: e.message
      }
      status(422)
      respondWith json.toString()
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
