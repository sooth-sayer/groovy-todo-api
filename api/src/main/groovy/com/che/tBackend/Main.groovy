package tBackend
import groovy.transform.CompileStatic

import tBackend.db.Orm
import tBackend.models.*
import tBackend.controllers.UsersController
import tBackend.controllers.SessionsController

import io.vertx.groovy.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.groovy.ext.web.Router
import io.vertx.groovy.ext.web.handler.BodyHandler


// @CompileStatic
class Main {
  static main(args) {

    def db = new Orm()
    db.init()

    def vertx = Vertx.vertx()
    def router = Router.router(vertx)
    router.route().handler(BodyHandler.create())

    router.route(HttpMethod.GET, "/profile").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new UsersController(routingContext)
      response.setChunked(true)
      handler.get()
      response.end()
    })

    router.route(HttpMethod.POST, "/users").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new UsersController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.POST, "/sessions").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new SessionsController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.DELETE, "/sessions").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new SessionsController(routingContext)
      response.setChunked(true)
      handler.delete()
      response.end()
    })


    def server = vertx.createHttpServer()
    server.requestHandler(router.&accept)
    server.listen(8080)
  }
}

