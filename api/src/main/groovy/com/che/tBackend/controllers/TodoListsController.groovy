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

    params.user_id = currentUser.id
    def todoList = new TodoList(params)

    if (!todoList.validate()) {
      status(422)
      respondWith errors: todoList.errors
    }

    todoList.save()
    status(201)
    respondWith id: todoList.id, name: todoList.name
  }

  def index() {
    if (!isAuthorized()) {
      status(403)
      return
    }

    respondWith currentUser.todos
  }

  def get() {
    if (!isAuthorized()) {
      status(403)
      return
    }

    def todolistId = params.get("todolist_id") as Integer
    def todoList = currentUser.todos.find { it.id == todolistId }
    if (!todoList) {
      status(404)
      respondWith errors: {
        other message: "Todolist is not exists"
      }
      return
    }

    respondWith todoList
  }

  def delete() {
    if (!isAuthorized()) {
      status(403)
      return
    }

    def todolistId = params.get("todolist_id") as Integer
    def todoList = currentUser.todos.find { it.id == todolistId }
    if (!todoList) {
      status(404)
      respondWith errors: {
        other message: "Todolist is not exists"
      }
      return
    }

    todoList.delete()
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
