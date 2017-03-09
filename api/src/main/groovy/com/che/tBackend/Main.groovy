package tBackend
import groovy.transform.CompileStatic

import tBackend.db.Orm
import tBackend.models.*
import tBackend.controllers.UsersController
import tBackend.controllers.SessionsController
import tBackend.controllers.TodoListsController
import tBackend.controllers.ItemsController
import tBackend.routing.JsonOnlyHandler

import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.ResponseContentTypeHandler

// @CompileStatic
class Main {
  static main(args) {

    def env = System.getenv()
    def host = env['DB_HOST']
    def user = env['DB_USER']
    def password = env['DB_PASSWORD']
    def url = "jdbc:postgresql://${host}:5432/postgres"

    def db = new Orm(url, user, password)

    def vertx = Vertx.vertx()
    def router = Router.router(vertx)
    router.route().handler(BodyHandler.create())
    router.route("/api/*").handler(ResponseContentTypeHandler.create())
    router.route(HttpMethod.POST, "/api/*").handler(JsonOnlyHandler.create())

    router.route(HttpMethod.POST, "/api/v1/users").produces("application/json").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new UsersController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.POST, "/api/v1/sessions").produces("application/json").handler({ routingContext ->
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

    router.route(HttpMethod.POST, "/api/v1/todolists").produces("application/json").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new TodoListsController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.GET, "/api/v1/todolists").produces("application/json").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new TodoListsController(routingContext)
      response.setChunked(true)
      handler.index()
      response.end()
    })

    router.route(HttpMethod.GET, "/api/v1/todolists/:todolist_id").produces("application/json").handler({ routingContext ->
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

    router.route(HttpMethod.GET, "/api/v1/todolists/:todolist_id/items").produces("application/json").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new ItemsController(routingContext)
      response.setChunked(true)
      handler.index()
      response.end()
    })

    router.route(HttpMethod.POST, "/api/v1/todolists/:todolist_id/items").produces("application/json").handler({ routingContext ->
      def response = routingContext.response()
      def handler = new ItemsController(routingContext)
      response.setChunked(true)
      handler.post()
      response.end()
    })

    router.route(HttpMethod.GET, "/api/v1/todolists/:todolist_id/items/:item_id").produces("application/json").handler({ routingContext ->
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
    server.listen(80, { res ->
      if (res.succeeded()) {
        db.init()
      }
    })
  }
}

