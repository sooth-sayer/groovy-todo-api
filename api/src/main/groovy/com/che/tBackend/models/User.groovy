package tBackend.models

import gstorm.Table
import gstorm.Id

@Table(value="TodoUser", idDefinition="SERIAL PRIMARY KEY")
class User {
  @Id
  Integer id
  String email
  String password_encrypted

  TodoList[] getTodos() {
    TodoList.where("user_id = '$id'")
  }
}
