package tBackend.models

import tBackend.db.models.DbUser
import tBackend.lib.Encryptor

class User extends DbUser {
  TodoList[] getTodos() {
    TodoList.where("user_id = '$id'")
  }

  String password
  String password_confirmation

  Map errors

  // TODO: validators
  def validate() {
    def passwordErrors = []
    def passwordConfirmationErrors = []
    def emailErrors = []

    if (!email) {
      emailErrors << [ type: "blank", message: "Can't be blank" ]
    }

    if (User.count("email = '$email'") > 0) {
      emailErrors << [ type: "exists", message: "Already exists" ]
    }

    if (!password) {
      passwordErrors << [ type: "blank", message: "Can't be blank" ]
    }

    if (!password_confirmation) {
      passwordConfirmationErrors << [ type: "blank", message: "Can't be blank" ]
    }

    if (password != password_confirmation) {
      passwordConfirmationErrors << [ type: "unequal", message: "Password confirmation doesn't match password" ]
    }

    errors = [
      email: emailErrors,
      password: passwordErrors,
      password_confirmation: passwordConfirmationErrors,
    ]

    passwordErrors.isEmpty() && passwordConfirmationErrors.isEmpty() && emailErrors.isEmpty()
  }

  def save() {
    if (!validate()) {
      throw new Exception("User is not valid: ${errors}")
    }

    password_encrypted = Encryptor.encrypt(password)
    super.save()
  }
}
