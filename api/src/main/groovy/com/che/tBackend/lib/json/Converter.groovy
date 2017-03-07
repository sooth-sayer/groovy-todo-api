package tBackend.lib.json

import tBackend.lib.annotations.ExcludeFromSerialize
import tBackend.lib.annotations.IncludeToSerialize
import groovy.extjson.JsonGenerator

class Converter implements JsonGenerator.Converter {
  private Class<?> type
  Converter(Class<?> type) {
    this.type = type
  }

  static Converter buildFor(Class<?> type) {
    new Converter(type)
  }

  @Override
  boolean handles(Class<?> type) {
    this.type == type
  }

  @Override
  Object convert(Object value, String key) {
    def toSerialize = [*getFields(type), *getProperties(type)]
    toSerialize.collectEntries { [(it), value.getProperty(it)] }
  }

  static private def getFields(Class<?> type) {
    [*type.declaredFields, *type.getSuperclass().declaredFields]
      .findAll {
        !it.synthetic && !it.isAnnotationPresent(ExcludeFromSerialize)
      }
      .collect { it.name }
  }

  static private def getProperties(Class<?> type) {
    type.metaClass.properties
      .findAll {
        def getterName = it.getter?.name
        if (getterName) {
          def method = type.methods.find { it.name == getterName }
          method && method.isAnnotationPresent(IncludeToSerialize)
        }
      }
      .collect { it.name }
  }
}
