package tBackend.models

import tBackend.db.models.DbItem

class Item extends DbItem {
  TodoList getTodoList() {
    TodoList.where("id = '${todolist_id}'")
  }
}
