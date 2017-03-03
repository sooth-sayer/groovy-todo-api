package tBackend.models

import gstorm.Table
import gstorm.Id

@Table(value="TodoList", idDefinition="SERIAL PRIMARY KEY")
class TodoList {
  @Id
  Integer id
  String name
  Integer user_id

  Item[] getItems() {
    Item.where("todolist_id = '$id'")
  }

  User getUser() {
    User.where("id = '$user_id'")
  }
}
