package tBackend.controllers.traits

import groovy.json.JsonBuilder

trait RespondWithJson {
  def respondWith(body) {
    def json = new JsonBuilder()
    json body
    super.respondWith(json.toString())
  }
}
