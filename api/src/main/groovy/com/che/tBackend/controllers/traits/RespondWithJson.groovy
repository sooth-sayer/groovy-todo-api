package tBackend.controllers.traits

import groovy.extjson.JsonBuilder
import groovy.extjson.JsonGenerator
import tBackend.models.*
import tBackend.lib.json.Converter

trait RespondWithJson {
  def respondWith(body) {
    def json = new JsonBuilder(body, getGenerator())
    super.respondWith json.toString()
  }

  static def getGenerator() {
    def generator = new JsonGenerator.Options()
    getConverters().each { generator.addConverter it }
    generator.build()
  }

  static def getConverters() {
    [
      new Converter(User.class),
      new Converter(TodoList.class),
      new Converter(Item.class)
    ]
  }
}
