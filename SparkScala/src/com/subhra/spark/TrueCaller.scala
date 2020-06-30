package com.subhra.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._ 

object TrueCaller {
  
def main(args: Array[String]) {
  
  val spark = SparkSession
  .builder()
  .master("local[*]")
  //.config("spark.some.config.option", "some-value")
  .getOrCreate()
  
  import spark.implicits._
  import spark.sqlContext.implicits._
  
  val schema = StructType(Array(StructField("userID", IntegerType, true),
          StructField("URL", StringType, true),
          StructField("visitedTime", StringType, true)))
//Apply Shema and read data to a dataframe
val myDF = spark.read.format("csv")
          .option("header", "false")
          .schema(schema)
          .load("hdfs:///user/retail.csv")
          
//def lead(Column e, int offset):Column
myDF.persist()
          
val wind = Window.partitionBy("userID").orderBy("visitedTime")
val conv = myDF.withColumn("visitTime",to_timestamp(col("visitedTime")))

val result = conv.withColumn("leadCol", lead(col("visitTime"), 1).over(wind))
val finalResult = result.withColumn("diff", col("leadCol").cast(LongType) - col("visitTime").cast(LongType)).select("userID","URL","visitedTime","diff")
          
  finalResult.show()
  
  spark.stop()

    
  }
  
}