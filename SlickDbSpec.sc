import $ivy.`org.scalatest::scalatest:3.0.1` 
import $ivy.`com.typesafe.slick::slick:3.2.3`
import $ivy.`com.typesafe.slick::slick-hikaricp:3.2.3`
import $ivy.`org.slf4j:slf4j-nop:1.6.4`
import $ivy.`com.h2database:h2:1.4.197`

import slick.jdbc.H2Profile.api._
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest._
import scala.concurrent.{ Await }
import scala.concurrent.duration._

// Definition of the SUPPLIERS table
class Suppliers(tag: slick.jdbc.H2Profile.api.Tag) extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {
  def id = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
  def name = column[String]("SUP_NAME")
  def street = column[String]("STREET")
  def city = column[String]("CITY")
  def state = column[String]("STATE")
  def zip = column[String]("ZIP")
  // Every table needs a * projection with the same type as the table's type parameter
  def * = (id, name, street, city, state, zip)
}
val suppliers = TableQuery[Suppliers]

// Definition of the COFFEES table
class Coffees(tag: slick.jdbc.H2Profile.api.Tag) extends Table[(String, Int, Double, Int, Int)](tag, "COFFEES") {
  def name = column[String]("COF_NAME", O.PrimaryKey)
  def supID = column[Int]("SUP_ID")
  def price = column[Double]("PRICE")
  def sales = column[Int]("SALES")
  def total = column[Int]("TOTAL")
  def * = (name, supID, price, sales, total)
  // A reified foreign key relation that can be navigated to create a join
  def supplier = foreignKey("SUP_FK", supID, suppliers)(_.id)
}
val coffees = TableQuery[Coffees]
val setup = DBIO.seq(
  // Create the tables, including primary and foreign keys
  (suppliers.schema ++ coffees.schema).create,

  // Insert some suppliers
  suppliers += (101, "Acme, Inc.",      "99 Market Street", "Groundsville", "CA", "95199"),
  suppliers += ( 49, "Superior Coffee", "1 Party Place",    "Mendocino",    "CA", "95460"),
  suppliers += (150, "The High Ground", "100 Coffee Lane",  "Meadows",      "CA", "93966"),
  // Equivalent SQL code:
  // insert into SUPPLIERS(SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP) values (?,?,?,?,?,?)

  // Insert some coffees (using JDBC's batch insert feature, if supported by the DB)
  coffees ++= Seq(
    ("Colombian",         101, 7.99, 0, 0),
    ("French_Roast",       49, 8.99, 0, 0),
    ("Espresso",          150, 9.99, 0, 0),
    ("Colombian_Decaf",   101, 8.99, 0, 0),
    ("French_Roast_Decaf", 49, 9.99, 0, 0)
  )
  // Equivalent SQL code:
  // insert into COFFEES(COF_NAME, SUP_ID, PRICE, SALES, TOTAL) values (?,?,?,?,?)
)
val cleanup = DBIO.seq(
 (suppliers.schema ++ coffees.schema).drop
)

class SlickDbSpec extends WordSpec with Matchers with BeforeAndAfterEach {
  val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")
  implicit val timeout = 5.seconds

  override def beforeEach() {
     Await.result(db.run(setup), timeout)
  }
  override def afterEach() {
     Await.result(db.run(cleanup), timeout)
  } 

  "Database" can {
    "be initialized, have records and be dropped" in {
      val q1 = suppliers.result
      val vector1 = Await.result(db.run(q1), timeout)
      vector1.length should be >= 2

      val q2 = coffees.result
      val vector2 = Await.result(db.run(q2), timeout)
      vector2.length should be >= 2
    }
  }
}

(new SlickDbSpec).execute

