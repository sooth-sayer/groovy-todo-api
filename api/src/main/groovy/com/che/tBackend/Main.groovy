package tBackend
import groovy.transform.CompileStatic

import tBackend.db.Orm
import tBackend.models.*

// @CompileStatic
class Main {
  static main(args) {

    def db = new Orm()
    db.init()


    def u1 = new User(email: 'd.chernyatiev@gmail.com')
    u1.save()

    def list = new TodoList(name: 'Test list', user_id: u1.id)
    list.save()

    def item1 = new Item(name: "First item", description: "Some first todo item", todolist_id: list.id)
    def item2 = new Item(name: "Second item", description: "Some second item", todolist_id: list.id)
    item1.save()
    item2.save()

    def u = new User(User.all()[0])
    def items = u.todos[0].items.collect { it.description }
    println items

  }
}

