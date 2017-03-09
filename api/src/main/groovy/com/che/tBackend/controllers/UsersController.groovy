package tBackend.controllers

import groovy.transform.InheritConstructors
import tBackend.models.User
import tBackend.controllers.traits.RespondWithJson

@InheritConstructors
class UsersController extends ApplicationController implements RespondWithJson {
  def post() {
    def params
    try {
      params = permit()
    } catch (e) {
      status(422)
      respondWith errors: {
        other message: e.message
      }
      return
    }

    def user = new User(params)

    if (!user.validate()) {
      status(422)
      respondWith errors: user.errors
      return
    }

    user.save()
    status(201)
    respondWith id: user.id, email: user.email
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
