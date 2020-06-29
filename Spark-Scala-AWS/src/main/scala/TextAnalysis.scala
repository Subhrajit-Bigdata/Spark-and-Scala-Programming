import scala.util.Failure
import org.apache.spark.sql.{AnalysisException, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.functions.lit
 
// primary constructor
class TextAnalysis(path : String, comment : String) {
 
//aux cons takes no inputs
def this() {
  this("s3://hdp-hive-s3/test.txt", "no params")
}
 
//aux cons takes file path as input
def this(path : String) {
  this(path, "from s3")
}
 
  //create spark session
  private val spark = SparkSession
    .builder
    .master("local")
    .appName("sparkWithAws")
    .getOrCreate()
 
  //Create a SparkContext to initialize Spark
  private val sc = spark.sparkContext
 
  def ReadFile(): DataFrame = {
 
    val testSchema = StructType(Array(
      StructField("first", StringType, true),
      StructField("second", StringType, true),
      StructField("third", StringType, true)))
 
    try {
 
      val df = spark.read
        .format("csv")
        .option("delimiter", ";")
        .schema(testSchema)
        .load(path)
 
      val df1 = df.withColumn("comment", lit(comment))
      df1.show()
 
    } catch {
      case e: AnalysisException =&amp;gt; {
        println(s"FILE $path NOT FOUND... EXITING...")
      }
      case unknown: Exception =&amp;gt; {
        println("UNKNOWN EXCEPTION... EXITING...")
        println(Failure(unknown))
      }
    }
    def writeFile(df:DataFrame): DataFrame= {
    result = df.coalesce(1).write
      .format("csv")
      .option("header", "true")
      .save("s3://some_bucket/data/states/all_states/")
    }
    
  }
}
 
object TextAnalysis {
  def main(args: Array[String]): Unit = {
    val sep = ";"
    val argsArray = args.mkString(sep).split(sep)
 
    val ta = new TextAnalysis("s3a://hdp-hive-s3/test.txt")
    ta.ReadFile()
 
    val ta1 = new TextAnalysis(argsArray(0), argsArray(1))
    val df1 = ta1.ReadFile()
    ta1.writeFile(df1)
   
    val ta2 = new TextAnalysis()
    ta2.ReadFile()
    
    
  }
}
