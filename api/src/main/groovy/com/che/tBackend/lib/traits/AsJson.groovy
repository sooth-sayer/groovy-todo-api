package tBackend.lib.traits

import groovy.json.JsonSlurper

trait AsJson {
  private jsonSlurper = new JsonSlurper()
  def asJson() {
    jsonSlurper.parseText toString()
  }

  String toString() {
    super.toString()
  }
}
