// A non-empty array A consisting of N integers is given. 
// Array A represents numbers on a tape.

// Any integer P, such that 0 < P < N, splits this tape into two non-empty parts: 
// A[0], A[1], ..., A[P − 1] and A[P], A[P + 1], ..., A[N − 1].

// The difference between the two parts is the value of: 
// |(A[0] + A[1] + ... + A[P − 1]) − (A[P] + A[P + 1] + ... + A[N − 1])|

// In other words, it is the absolute difference between 
// the sum of the first part and the sum of the second part.

// Write a function
// that, given a non-empty array A of N integers, 
// returns the minimal difference that can be achieved.

// Write an efficient algorithm for the following assumptions:
// N is an integer within the range [2..100,000];
// each element of array A is an integer within the range [−1,000..1,000].

object Solution { 
  def solution(a: Array[Int]): Int  = {
      var sumLeft = 0
      var sumRight = a.reduce(_ + _)
      var minDiff = Math.abs(sumLeft - sumRight)
      for (index <- 1 until a.length) {
        val diff = Math.abs(sumLeft - sumRight)
        if (diff < minDiff) {
            minDiff = diff
        }
        sumLeft += a(index)
        sumRight -= a(index)
      }
      minDiff
  }
}

import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {
  test("(3,1,2,4,3) -> 1") {
    Solution.solution(Array(3, 1, 2, 4, 3)) should equal(1)
  }
}
(new SolutionSpec).execute
