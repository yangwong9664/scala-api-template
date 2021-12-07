package helpers

import database.MongoDB
import models.TestDB

import scala.concurrent.Await
import scala.concurrent.duration._

trait DataOperations {
  self: IntegrationSpecBase =>
  protected def mongoDB: MongoDB

  def insert(testDB: TestDB): Boolean = {
    Await.result(mongoDB.metadataCollection.insertOne(testDB).toFuture()
      .map {_ =>
        true
      }, 10.seconds)
  }

  def delete(id: String): Boolean = {
    Await.result(mongoDB.metadataCollection
      .findOneAndDelete(org.mongodb.scala.model.Filters.equal("_id",id)).toFuture()
      .map {_ =>
        true
      }, 10.seconds)
  }
}
