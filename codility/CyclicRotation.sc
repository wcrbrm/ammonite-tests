
// An array A consisting of N integers is given. 
// Rotation of the array means that each element is shifted right 
// by one index, and the last element of the array is moved to 
// the first place. For example, the rotation of array A = [3, 8, 9, 7, 6] is [6, 3, 8, 9, 7] (elements are shifted right by one index and 6 is moved to the first place).

// The goal is to rotate array A K times; 
// that is, each element of A will be shifted to the right 
// K times.

// That, given an array A consisting of N integers 
// and an integer K, returns the array A rotated K times.

// N and K are integers within the range [0..100];
// each element of array A is an integer within the range [−1,000..1,000].

object Solution {
   def solution(a: Array[Int], k: Int): Array[Int] = {
      val kLeft = k % a.size // do not rotate more than the size of array
      if (kLeft == 0) a
      else {
        val tail = a(a.length - 1)
        val rest = a.slice(0, a.length - 1)
        solution(tail +: rest, k - 1)
      }
   }
}


import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {

  // original spec:
  test("original spec K=3") {
    Solution.solution(Array[Int](3, 8, 9, 7, 6), 3)
        .toList should equal(List(9, 7, 6, 3, 8))
  }
  test("original spec, zeros K=1") {
    Solution.solution(Array[Int](0, 0, 0), 1)
        .toList should equal(List(0, 0, 0))
  }
  test("original spec, 1 to 4 K=4") {
    Solution.solution(Array[Int](1, 2, 3, 4), 4)
        .toList should equal(List(1, 2, 3, 4))
  }
}
(new SolutionSpec).execute
