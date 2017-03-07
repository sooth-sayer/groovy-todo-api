package tBackend.db.models

import gstorm.Table
import gstorm.Id

@Table(value="TodoList", idDefinition="SERIAL PRIMARY KEY")
class DbTodoList {
  @Id
  Integer id
  String name
  Integer user_id
}
