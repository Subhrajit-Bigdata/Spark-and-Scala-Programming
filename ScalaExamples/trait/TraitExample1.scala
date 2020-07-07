trait Alarm {
  def trigger(): String
}
trait Notifier {
  val notificationMessage: String

  def printNotification(): Unit = {
    println(notificationMessage)
  }

  def clear()
}

class NotifierImpl(val notificationMessage: String) extends
  Notifier {
  override def clear(): Unit = System.out.println("cleared")
}
abstract class Connector {
  def connect()
  def close()
}
trait ConnectorWithHelper extends Connector {
  def findDriver(): Unit = {
    println("Find driver called.")
  }
}
class PgSqlConnector extends ConnectorWithHelper {
  override def connect(): Unit = {
    println("Connected...")
  }
  override def close(): Unit = {
    println("Closed...")
  }
}
class Watch(brand: String, initialTime: Long) {
  def getTime(): Long = System.currentTimeMillis() - initialTime
}
object TraitExample1 extends App {
    val expensiveWatch = new Watch("expensive brand", 1000L) with
      Alarm with Notifier {
      override def trigger(): String = "The alarm was triggered."
      override def clear(): Unit = {
        System.out.println("Alarm cleared.")
      }
      override val notificationMessage: String = "Alarm is running!"
    }
    val cheapWatch = new Watch("cheap brand", 1000L) with Alarm {
      override def trigger(): String = "The alarm was triggered."
    }
    // show some watch usage.
    println(expensiveWatch.trigger())
    expensiveWatch.printNotification()
    println(s"The time is ${expensiveWatch.getTime()}.")
    expensiveWatch.clear()
    println(cheapWatch.trigger())
    println("Cheap watches cannot manually stop the alarm...")
    
    }
