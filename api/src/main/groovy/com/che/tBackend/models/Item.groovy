package tBackend.models

import gstorm.Table
import gstorm.Id

@Table(value="TodoItem", idDefinition="SERIAL PRIMARY KEY")
class Item {
  @Id
  Integer id
  String name
  String description
  Integer todolist_id

  TodoList getTodoList() {
    TodoList.where("id = '${todolist_id}'")
  }
}
