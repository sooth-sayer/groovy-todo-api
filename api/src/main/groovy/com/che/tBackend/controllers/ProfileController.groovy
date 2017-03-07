package tBackend.controllers

import groovy.json.JsonBuilder
import groovy.transform.InheritConstructors
import tBackend.models.User

@InheritConstructors
class ProfileController extends ApplicationController {
  def get() {
    def user = User
    respondWith("Test data to client!")
  }
}