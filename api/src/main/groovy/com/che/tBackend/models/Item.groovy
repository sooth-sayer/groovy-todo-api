package tBackend.models

import tBackend.db.models.DbItem
import tBackend.lib.annotations.ExcludeFromSerialize

class Item extends DbItem {
  TodoList getTodoList() {
    TodoList.where("id = '$todolist_id'")
  }

  @ExcludeFromSerialize
  Map errors = [:]

  def validate() {
    def nameErrors = []

    if (!name) {
      nameErrors << [ type: "blank", message: "Can't be blank" ]
    }

    errors = [
      name: nameErrors,
    ]

    nameErrors.isEmpty()
  }

  def save() {
    if (!validate()) {
      throw new Exception("Todo item is not valid: ${errors}")
    }

    super.save()
  }
}
