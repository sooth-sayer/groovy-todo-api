package tBackend.controllers

import io.vertx.core.http.HttpServerRequest
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
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

  def getParams() {
    context.request().params()
  }

  def respondWith(String content) {
    context.response().write(content)
  }

  def status(int status) {
    context.response().setStatusCode(status)
  }
}
