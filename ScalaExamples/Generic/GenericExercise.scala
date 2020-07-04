
  abstract class MyList[+A] {
    def head: A
    def tail: MyList[A]
    def isEmpty: Boolean
    def add[B >: A](element: B) : MyList[B]
    def printElement: String

    override def toString: String = s"{$printElement}"
    def map[B](transformer:MyTransformer[A,B]): MyList[B]

    def filter(predicate:MyPredicate[A]): MyList[A]
  }

  object Empty extends MyList[Nothing]{
    def head: Nothing = throw new NoSuchElementException
    def tail: MyList[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    //def add[B >: A](element: B): MyList[B] = ???
    def add[B >: Nothing](element: B) : MyList[B] = new Cons(element, Empty)
    def printElement: String = ""
    def map[B](transformer:MyTransformer[Nothing,B]): MyList[B] = Empty
    
    def filter(predicate:MyPredicate[Nothing]): MyList[Nothing] = Empty
  }
  class Cons[+A](b:A, t:MyList[A]) extends MyList[A]{
    def head:A = b
    def tail: MyList[A] = t
    def isEmpty: Boolean = false
 
    def add[B >: A](element: B) : MyList[B] = new Cons(element, this)
    def printElement: String =
      if (t.isEmpty) "" + b
      else b + " " + t.printElement
    def filter(predicate:MyPredicate[A]): MyList[A] =
      if (predicate.test(b)) new Cons(b,t.filter(predicate))
      else t.filter(predicate)

    def map[B](transformer:MyTransformer[A,B]): MyList[B] =
      new Cons(transformer.transform(b),t.map(transformer))
  }

  trait MyPredicate[-T]{
    def test(elem:T): Boolean
  }
  trait MyTransformer[-A, B]{
    def transform(elem: A ): B
  }

object GenericExerCise extends App{
  val listofIntegers:MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val ListofStrings:MyList[String] = new Cons("Subhrajit", new Cons("Behera", Empty))

  println(listofIntegers.toString)
  println(ListofStrings.toString)
  println(listofIntegers.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }).toString)
  println(listofIntegers.filter(new MyPredicate[Int] {
    override def test(elem: Int): Boolean = elem % 2 == 0
  }).toString)
}
