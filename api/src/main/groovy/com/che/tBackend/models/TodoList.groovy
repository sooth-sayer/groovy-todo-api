package tBackend.models

import tBackend.lib.annotations.IncludeToSerialize
import tBackend.lib.annotations.ExcludeFromSerialize
import tBackend.db.models.DbTodoList

class TodoList extends DbTodoList {

  @IncludeToSerialize
  Item[] getItems() {
    Item.where("todolist_id = '$id'")
  }

  User getUser() {
    User.where("id = '$user_id'")
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
      throw new Exception("Todo list is not valid: ${errors}")
    }

    super.save()
  }

}
