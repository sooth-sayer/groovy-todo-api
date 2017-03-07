package tBackend.db.models

import gstorm.Table
import gstorm.Id

@Table(value="TodoItem", idDefinition="SERIAL PRIMARY KEY")
class DbItem {
  @Id
  Integer id
  String name
  String description
  Integer todolist_id
}
