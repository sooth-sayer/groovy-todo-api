package tBackend.controllers

import io.vertx.groovy.core.http.HttpServerRequest
import io.vertx.groovy.core.http.HttpServerResponse
import io.vertx.groovy.ext.web.RoutingContext
import tBackend.lib.traits.AsJson

class BaseController {
  final RoutingContext context

  BaseController(RoutingContext context) {
    this.context = context
  }

  HttpServerRequest getRequest() {
    context.request()
  }

  HttpServerResponse getResponse() {
    context.response()
  }

  def getBody() {
    context.getBodyAsString() withTraits AsJson
  }

  def respondWith(String content) {
    context.response().write(content)
  }

  def status(int status) {
    context.response().setStatusCode(status)
  }
}
