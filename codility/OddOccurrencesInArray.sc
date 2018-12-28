
// A non-empty array A consisting of N integers is given. 
// The array contains an odd number of elements, 
// and each element of the array can be paired with another element
// that has the same value, except for one element that is left unpaired.

// That, given an array A consisting of N integers fulfilling 
// the above conditions, returns the value of the unpaired element

// Write an **efficient** algorithm for the following assumptions:

// N is an ODD integer within the range [1..1,000,000];
// each element of array A is an integer within the range [1..1,000,000,000];
// all but one of the values in A occur an even number of times.

object Solution { 
  def solution(a: Array[Int]): Int = {
    if (a.length == 0) throw new Exception("input array error: expected N>1")
    // if (a.length % 2 == 0) throw new Exception("input array error: odd number of items expected")
    if (a.length == 1) {
        a(0)
    } else {
        var unpaired = a(0)
        for (i <- 0 to (a.length-1)) { 
          // println(a(i), unpaired, i)
          unpaired = unpaired ^ a(i) 
        }
        unpaired
    }
  }
}

import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {
  // original spec:
  test("original (9,3,3,9,7,9) -> 7") {
    Solution.solution(Array[Int](9, 3, 3, 9, 7, 9)) should equal(7)
  }
  test("in another place (9, 3, 3, 9, 9, 7) -> 7") {
    Solution.solution(Array[Int](9, 3, 3, 9, 9, 7)) should equal(7)
  }
  test("in another place (7, 9, 3, 3, 9, 9) -> 7") {
    Solution.solution(Array[Int](7, 9, 3, 3, 9, 9)) should equal(7)
  }
}
(new SolutionSpec).execute
