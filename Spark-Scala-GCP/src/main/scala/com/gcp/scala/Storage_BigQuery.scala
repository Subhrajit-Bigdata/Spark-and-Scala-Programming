package com.gcp.scala

import com.google.api.services.bigquery.model.{TableFieldSchema, TableSchema}
import com.spotify.scio.bigquery._
import com.spotify.scio.ContextAndArgs
import com.google.api.services.bigquery.model.{Table, TableFieldSchema, TableSchema}
import com.spotify.scio._
import com.spotify.scio.bigquery._

import scala.collection.JavaConverters._

object Storage_Bigquery {
  def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)

    val schema = new TableSchema().setFields(
      List(
        new TableFieldSchema().setName("name").setType("STRING"),
        new TableFieldSchema().setName("year").setType("INTEGER"),
        new TableFieldSchema().setName("gender").setType("STRING"),
        new TableFieldSchema().setName("state").setType("STRING"),
        new TableFieldSchema().setName("count").setType("INTEGER")
      ).asJava
    )

    val exampleData = "gs://my-bigdata-bucket/babiescount.csv"
    val input = args.getOrElse("input", exampleData)
    //val output = args("output")

    sc.textFile(input)
      .map(x=> x.split(","))
      .map(kv => TableRow("name" -> kv(0), "year" -> kv(1), "gender" -> kv(2), "state" -> kv(3), "count" -> kv(4)))
      .saveAsBigQuery(args("output"), schema, WRITE_TRUNCATE, CREATE_IF_NEEDED)
    sc.close()
    ()

  }
}
