package tBackend.controllers

import groovy.transform.InheritConstructors
import tBackend.models.TodoList
import tBackend.models.Item
import tBackend.controllers.traits.RespondWithJson

@InheritConstructors
class ItemsController extends ApplicationController implements RespondWithJson {
  def post() {
    if (!isAuthorized()) {
      status(403)
      return
    }

    try {
      if (!isAuthorized()) {
        status(403)
        return
      }

      def todolistId = params.get("todolist_id") as Integer
      def todoList = currentUser.todos.find { it.id == todolistId }

      if (!todoList) {
        status(404)
        respondWith errors: {
          other message: "Todo list is not exists"
        }
        return
      }

      def itemParams = permit()
      itemParams.todolist_id = todolistId
      def item = new Item(itemParams)

      if (!item.validate()) {
        status(422)
        respondWith errors: item.errors
      }

      item.save()
      respondWith id: item.id, name: item.name, description: item.description
    } catch (groovy.json.JsonException e) {
      status(422)
      respondWith errors: {
        other message: e.message
      }
    }
  }

  def index() {
    if (!isAuthorized()) {
      status(403)
      return
    }

    def todolistId = params.get("todolist_id") as Integer
    def todoList = currentUser.todos.find { it.id == todolistId }
    if (!todoList) {
      status(404)
      respondWith errors: {
        other: message: e.message
      }
      return
    }

    respondWith todoList.items
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

    def itemId = params.get("item_id") as Integer
    def item = todoList.items.find { it.id == itemId }
    if (!item) {
      status(404)
      respondWith errors: {
        other message: "Item is not exists"
      }
      return
    }

    respondWith item
  }

  def permit() {
    def body = body.asJson()
    def user = [:]

    ["name", "description"].each {
      user[it] = body[it]
    }

    user
  }
}

