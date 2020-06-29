object Main extends App {
  val persons = List(
    Person(firstName = "Biswajit", lastName = "Acharya", age = 12),
    Person(firstName = "Satyajit", lastName = "Mohapatra", age = 34),
    Person(firstName = "Mir", lastName = "Abdul", age = 52))
  val adults = Person.filterAdult(persons)
  val descriptions = adults.map(p => p.description).mkString("\n\t")
  println(s"The adults are \n\t$descriptions")
}
