package tBackend
import groovy.transform.CompileStatic

import tBackend.db.Orm
import tBackend.models.*
import tBackend.controllers.UsersController
import tBackend.controllers.SessionsController
import tBackend.controllers.TodoListsController
import tBackend.controllers.ItemsController

import io.vertx.groovy.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.groovy.ext.web.Router
import io.vertx.groovy.ext.web.handler.BodyHandler

// @CompileStatic
class Main {
  static main(args) {

    def env = System.getenv()
    def host = env['DB_HOST']
    def user = env['DB_USER']
    def password = env['DB_PASSWORD']
    def url = "jdbc:postgresql://${host}:5432/postgres"

    def db = new Orm(url, user, password)
    db.init()

    def vertx = Vertx.vertx()
    def router = Router.router(vertx)
    router.route().handler(BodyHandler.create())

    router.route(HttpMethod.POST, "/api/v1/users").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new UsersController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.POST, "/api/v1/sessions").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new SessionsController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.DELETE, "/api/v1/sessions").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new SessionsController(routingContext)
      response.setChunked(true)
      handler.delete()
      response.end()
    })

    router.route(HttpMethod.POST, "/api/v1/todolists").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new TodoListsController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.GET, "/api/v1/todolists").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new TodoListsController(routingContext)
      response.setChunked(true)
      handler.index()
      response.end()
    })

    router.route(HttpMethod.GET, "/api/v1/todolists/:todolist_id").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new TodoListsController(routingContext)
      response.setChunked(true)
      handler.get()
      response.end()
    })

    router.route(HttpMethod.DELETE, "/api/v1/todolists/:todolist_id").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new TodoListsController(routingContext)
      response.setChunked(true)
      handler.delete()
      response.end()
    })

    router.route(HttpMethod.GET, "/api/v1/todolists/:todolist_id/items").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new ItemsController(routingContext)
      response.setChunked(true)
      handler.index()
      response.end()
    })

    router.route(HttpMethod.POST, "/api/v1/todolists/:todolist_id/items").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new ItemsController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.GET, "/api/v1/todolists/:todolist_id/items/:item_id").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new ItemsController(routingContext)
      response.setChunked(true)
      handler.get()
      response.end()
    })

    router.route(HttpMethod.DELETE, "/api/v1/todolists/:todolist_id/items/:item_id").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new ItemsController(routingContext)
      response.setChunked(true)
      handler.delete()
      response.end()
    })

    def server = vertx.createHttpServer()
    server.requestHandler(router.&accept)
    server.listen(80)
  }
}

