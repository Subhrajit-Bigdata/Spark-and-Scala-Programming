trait Speaker {
    def speak(): String   //abstract
}

trait TailWagger {
    def startTail(): Unit = println("tail is wagging")
    def stopTail(): Unit = println("tail is stopped")
}

trait Runner {
    def startRunning(): Unit = println("I'm running")
    def stopRunning(): Unit = println("Stopped running")
}

class Cat extends Speaker with TailWagger with Runner {
    def speak(): String = "Meow"
    override def startRunning(): Unit = println("Yeah ... I don't run")
    override def stopRunning(): Unit = println("No need to stop")
}

class Dog(name: String) extends Speaker with TailWagger with Runner {
    def speak(): String = "Woof!"
}

object TraitExample extends App{
    val c = new Cat
	val d = new Dog("Kallu")
	
	println(c.speak)
	println(d.speak)
	
}
