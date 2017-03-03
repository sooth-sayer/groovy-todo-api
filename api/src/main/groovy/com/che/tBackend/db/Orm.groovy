package tBackend.db

import groovy.sql.Sql
import gstorm.Gstorm
import tBackend.models.*

class Orm {
  private String url
  private String user
  private String password
  private String driver

  Orm(
    url = 'jdbc:postgresql://db:5432/postgres',
    user = 'postgres',
    password = '123456',
    driver = 'org.postgresql.Driver'
  ) {
    this.url = url
    this.user = user
    this.password = password
    this.driver = driver
  }

  def init() {
    def sql = Sql.newInstance url, user, password, driver
    def g = new Gstorm(sql)

    g.stormify User
    g.stormify TodoList
    g.stormify Item
  }
}
