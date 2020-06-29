case class People(firstName: String, lastName: String, age: Int) {
  def description = s"$firstName $lastName is $age ${if (age <= 1) "year" else "years"} old"
}

object People {
  def filterAdult(peoples: List[People]) : List[] = {
    peoples.filter(_.age >= 18)
  }
}
