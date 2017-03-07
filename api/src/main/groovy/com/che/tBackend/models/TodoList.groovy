package tBackend.models

import tBackend.db.models.DbTodoList

class TodoList extends DbTodoList {
  Item[] getItems() {
    Item.where("todolist_id = '$id'")
  }

  User getUser() {
    User.where("id = '$user_id'")
  }
}
