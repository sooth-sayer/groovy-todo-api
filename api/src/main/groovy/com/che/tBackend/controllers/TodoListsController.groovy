package tBackend.controllers

import groovy.transform.InheritConstructors
import tBackend.models.TodoList
import tBackend.models.User
import tBackend.controllers.traits.RespondWithJson

@InheritConstructors
class TodoListsController extends ApplicationController implements RespondWithJson {
  def post() {
    if (!isAuthorized()) {
      status(403)
      return
    }

    try {
      def params = permit()
      params.user_id = currentUser.id
      def todoList = new TodoList(params)

      if (!todoList.validate()) {
        status(422)
        respondWith errors: todoList.errors
      }

      todoList.save()
      respondWith id: todoList.id, name: todoList.name
    } catch (groovy.json.JsonException e) {
      status(422)
      respondWith errors {
        other message: e.message
      }
    }
  }

  def index() {
    if (!isAuthorized()) {
      status(403)
      return
    }

    respondWith currentUser.todos
  }

  def permit() {
    def body = body.asJson()
    def user = [:]

    ["name"].each {
      user[it] = body[it]
    }

    user
  }
}
