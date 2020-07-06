/*simplified social network based on maps
        Person = String
        - add a person to the network
        - remove
        - friend (mutual)
        - unfriend
        - number of friends of a person
        - person with most friends
        - how many people have NO friends */
        
object MapNdFlatmap extends App{

/*  val lis = List(1,2,3)
  println(lis.map(_ * 2))

  //list
  val alist = List(1,2,3)
  val prepend = 42 +: alist :+ 89
  println(prepend)  //List(42 +: alist :+ 89

  val appales = List.fill(3)("apple")
  println(appales)  //List(apple,apple,apple)

  println(alist.mkString("-")) //1-2-3
  val currenttime = System.nanoTime()
  println(currenttime)

  val tup = (2,"hello scala")
  println(tup.copy(_2 = "hello python"))

  val phonebook = Map(("Kulu", 767), "Chiku" -> 965).withDefaultValue(-1)
  println(phonebook)
 println(phonebook.map(x => x._1.toLowerCase -> x._2))
  println(phonebook.view.mapValues(num => "367" +  num))*/

def add(network: Map[String, Set[String]], person:String): Map[String,Set[String]] =
  network + (person -> Set())

  def friend(network: Map[String, Set[String]], a:String, b:String): Map[String,Set[String]] = {
    val friendaA = network(a)
    val friendsB = network(b)
    network + (a -> (friendaA + b)) + (b -> (friendsB + a))
  }
  def unfriend(network: Map[String, Set[String]], a:String, b:String): Map[String,Set[String]] = {
    val friendaA = network(a)
    val friendsB = network(b)
    network + (a -> (friendaA - b)) + (b -> (friendsB - a))
  }
  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  // Jim,Bob,Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriends(testNet, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1

  println(mostFriends(testNet))

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.count(_._2.isEmpty)

  println(nPeopleWithNoFriends(testNet))
}
