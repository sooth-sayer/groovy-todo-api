package tBackend.db.models

import gstorm.Table
import gstorm.Id
import gstorm.Constraints

@Table(value="TodoUser", idDefinition="SERIAL PRIMARY KEY")
class DbUser {
  @Id
  Integer id

  @Constraints("UNIQUE")
  String email

  String password_encrypted
}
