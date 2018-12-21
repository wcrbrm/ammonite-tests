import $ivy.`org.scalatest::scalatest:3.0.1` 
import org.scalatest.FunSuite

class SetSuite extends FunSuite {             
 test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
 }
 test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
 }
}

(new SetSuite).execute 
