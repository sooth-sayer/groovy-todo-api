package tBackend.routing

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class JsonOnlyHandler implements Handler<RoutingContext> {
  static Handler<RoutingContext> create() {
    new JsonOnlyHandler()
  }

  void handle(RoutingContext routingContext) {
    def headers = routingContext
      .request()
      .headers()

    if (headers["Content-Type"] == "application/json") {
      routingContext.next()
    } else {
      def response = routingContext.response()
      response.putHeader("Content-Type", "text/plaint")
      response.end("Wrong content type, must be application/json")
    }
  }
}
