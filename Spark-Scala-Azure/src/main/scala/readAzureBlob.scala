package org.abdh.poc

import org.apache.log4j.{Level, LogManager}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object readAzureBlob {
  def main(args: Array[String]): Unit = {
    val ssc = loadSpark()
    ssc.conf.set("fs.wasbs.impl","org.apache.hadoop.fs.azure.NativeAzureFileSystem")
    ssc.conf.set("fs.azure.account.key.dctblobstorage.blob.core.windows.net",
      "ZMWfYQGbi3wrmM/1GMGKs5lJFqNb8TMQkxvN1yDKTVC/Z9/07UrjhgA0OHIQ==")
    LogManager.getRootLogger.setLevel(Level.ERROR)

    val format = new java.text.SimpleDateFormat("MM/dd/yyyy")
   //val df = ssc.read.csv("wasbs://dct-container@dctblobstorage.blob.core.windows.net/businessData.csv").show()
    val dfH = ssc.read.option("header", "true")
      .option("inferSchema", "true")
      .option("encode","UTF-8")
      .format(formatpass(args(0)))
      .load(args(0)) //"wasbs://dct-container@dctblobstorage.blob.core.windows.net/dct-licence-dept/Hotel.csv")
      .withColumn("Valid From",date_format(to_date(col("Valid From"), "dd/MM/yyyy"), "yyyy-MM-dd"))
      .withColumn("Valid To",date_format(to_date(col("Valid TO"), "dd/MM/yyyy"), "yyyy-MM-dd"))
    //dfH.show()
   // dfH.printSchema()

    val dfR = ssc.read.option("header", "true")
      .option("inferSchema", "true")
      .option("encode","UTF-8")
      .format(formatpass(args(1)))
      .load(args(1))//"wasbs://dct-container@dctblobstorage.blob.core.windows.net/dct-licence-dept/Resturant.csv")
      .withColumn("Valid From",date_format(to_date(col("Valid From"), "dd/MM/yyyy"), "yyyy-MM-dd"))
      .withColumn("Valid To",date_format(to_date(col("Valid To"), "dd/MM/yyyy"), "yyyy-MM-dd"))
      .withColumn("Approved Date",date_format(to_date(col("Approved Date"), "dd/MM/yyyy"), "yyyy-MM-dd"))
    //dfR.show()
    dfR.printSchema()

    val dfS = ssc.read.option("header", "true")
      .option("inferSchema", "true")
      .option("encode","UTF-8")
      .format(formatpass(args(2)))
      .load(args(2))//"wasbs://dct-container@dctblobstorage.blob.core.windows.net/dct-licence-dept/shop.json")
      .withColumn("Valid From",date_format(to_date(col("Valid From"), "MM/dd/yyyy"), "yyyy-MM-dd"))
      .withColumn("Valid To",date_format(to_date(col("Valid To"), "MM/dd/yyyy"), "yyyy-MM-dd"))
    //dfS.show()
    //dfS.printSchema()

    dfH.union(dfS).withColumn("Approve Date", lit(null)).show()


  }


  def formatpass(rawpath: String):String ={
    rawpath.reverse.split("/").head.replace(".","/").split("/").head.reverse
  }
  def loadSpark() : SparkSession = {
    System.setProperty("hadoop.home.dir", "C:\\winutils\\")
    SparkSession.builder().master("local").appName("AzureBlobRead").getOrCreate()
  }

}
