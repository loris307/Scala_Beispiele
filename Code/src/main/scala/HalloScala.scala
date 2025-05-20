import akka.actor._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scala.util.{Failure, Success}

object HalloScala extends App {
  println("Hallo Scala!")

  //Variablen
  var name = "Loris"      // Variable
  val weed:Double = 4.20   // Konstante

  //if
  val note = if (weed >= 4.20) "bestanden" else "durchgefallen"

  //for-schleifen
  val quadrate = for (n <- 1 to 5) yield n * n  // Vector(1,4,9,16,25)
  quadrate.foreach(element=>{
    element match {
      case 1 => println("Das ist eine 1")
      case 16 => println("Das ist eine 16")
      case _ => println("Nix lol")
    }
  })

  //match
  val i = 1

  val ergebnis = i match {
    case 1 => "eins"
    case 2 | 3 => "zwei oder drei"
    case _ => "etwas anderes"
  }

  val list: List[Any] = List(1, "two", 3, "four", true)

  list.foreach { item =>
    item match {
      case i: Int => println(s"Found an Integer: $i")
      case s: String => println(s"Found a String: $s")
      case _ => println(s"Found an unknown type: $item")
    }
  }

  //collections
  val zahlen     = List(1, 2, 3)
  val erweitert  = zahlen :+ 4
  val telefonbuch = Map("Anna" -> "123", "Ben" -> "456")

  //try-catch:
  try {
    val lines = scala.io.Source.fromFile("data.txt").getLines()
    lines.foreach(println)
  } catch {
    case ex: java.io.FileNotFoundException =>
      println(s"Datei nicht gefunden: ${ex.getMessage}")
    case ex: Throwable =>
      println(s"Unbekannter Fehler: ${ex.getMessage}")
  } finally {
    println("Aufräumarbeiten abgeschlossen.")
  }

  //funktionen
  val tolleFunktion:Int => Int = (x) => x + 420

  val funktionHoehererOrdnung: (Int => Int) => Int = (f) => f(10)
  funktionHoehererOrdnung((x) => x + 420)

  //Instanzen
  val loris = new Youtuber("Loris", 24)
  loris.leak() //Loris ist 24 Jahre alt

  //statische Methode
  Youtuber.shoutout()

  //Beispiel für Case-Class
  val person = Employee("Loris", 24, 420)

  //Unapply Methode in Case-Class verwenden:
  val result = person match {
    case Employee(name, _, income) if (income > 419) => {
      name + " do you want to donate?"
    }
    case _ => "This person does not meet the donation criteria."
  }
  println(result)

  //Generische Typen
  var cannabisBox = new Box[Int](420)
  println(cannabisBox.drug) //420

  //Futures:
  val futureData = Future {
    // Simulierter API Call – lädt eine Webseite
    val result = Source.fromURL("https://jsonplaceholder.typicode.com/todos/1")
    result.getLines().mkString("\n")
  }

  println("Ich mach schonmal...")

  futureData.onComplete {
    case Success(data) => println(s"✅ API Response:\n$data")
    case Failure(e)    => println(s"❌ Fehler beim Laden: ${e.getMessage}")
  }

  // Damit das Programm nicht sofort weitermacht:
  Thread.sleep(3000)

  //Akka nutzung - siehe unten den Aktor und die Case-Class "SagHallo"
  val system = ActorSystem("YoutuberSystem")
  val halloActor = system.actorOf(Props[HalloActor], "halloActor")
  halloActor ! SagHallo("Loris")

  system.terminate()


}

//Klasse Youtuber
class Youtuber(val name:String, val alter:Int){
  def leak():Unit={
    println(s"$name ist $alter Jahre alt")
  }
}
//Companion-Objekt für statische Sachen
object Youtuber {
  def shoutout():Unit ={
    println("Du musst Loris Galler abonnieren!")
  }
}

//Vererbung und Traits
class YoutuberKind(name: String, alter: Int) extends Youtuber(name, alter) with Friend{

}

trait Friend {
  val name: String
  def isFriend() = println(s"Your friend is $name")
}

//Case-Class
case class Employee(name: String, age: Int, income: Int)

//generisch typisierte Klasse:
class Box[F <: AnyVal](val drug: F){}


//Akka
case class SagHallo(name: String)

class HalloActor extends Actor {
  def receive = {

    case SagHallo(name) => println(s"Hallo $name!")

    case _ => println("Was?")
  }
}