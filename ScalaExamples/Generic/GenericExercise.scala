
  abstract class MyList[+A] {
    def head: A
    def tail: MyList[A]
    def isEmpty: Boolean
    def add[B >: A](element: B) : MyList[B]
    def printElement: String

    override def toString: String = s"{$printElement}"
  }

  object Empty extends MyList[Nothing]{
    def head: Nothing = throw new NoSuchElementException
    def tail: MyList[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    //def add[B >: A](element: B): MyList[B] = ???
    def add[B >: Nothing](element: B) : MyList[B] = new Cons(element, Empty)
    def printElement: String = ""
  }
  class Cons[+A](b:A, t:MyList[A]) extends MyList[A]{
    def head:A = b
    def tail: MyList[A] = t
    def isEmpty: Boolean = false
    //def add[B >: A](element: B): MyList[B] = ???
    def add[B >: A](element: B) : MyList[B] = new Cons(element, this)
    def printElement: String =
      if (t.isEmpty) "" + b
      else b + " " + t.printElement
  }

object GenericExercise extends App{
  val listofIntegers:MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val ListofStrings:MyList[String] = new Cons("Subhrajit", new Cons("Behera", Empty))

  println(listofIntegers.toString)
  println(ListofStrings.toString)
}
