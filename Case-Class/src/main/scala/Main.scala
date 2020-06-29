object Main extends App {
  val peoples = List(
    People(firstName = "Biswajit", lastName = "Acharya", age = 12),
    People(firstName = "Satyajit", lastName = "Mohapatra", age = 34),
    People(firstName = "Mir", lastName = "Abdul", age = 52))
  val adults = Person.filterAdult(peoples)
  val descriptions = adults.map(p => p.description).mkString("\n\t")
  println(s"The adults are \n\t$descriptions")
}
