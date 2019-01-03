// A small frog wants to get to the other side of the road. 
// The frog is currently located at position X and wants to get 
// to a position greater than or equal to Y. 
// The small frog always jumps a fixed distance, D.

// Count the minimal number of jumps that the small frog must perform to reach its target.

// Write an **efficient** algorithm for the following assumptions:
// X, Y and D are integers within the range [1..1,000,000,000];
// X ≤ Y.

object Solution { 
    def roundUp: Double => Int = x => if (x > x.toInt) x.toInt + 1 else x.toInt
    def solution(x: Int, y: Int, d: Int): Int = roundUp((y.toDouble - x.toDouble) / d)
}

import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {
  test("roundUp(0.1)") { Solution.roundUp(0.1) should equal(1) }
  test("roundUp(1)") { Solution.roundUp(1) should equal(1) }
  test("roundUp(10)") { Solution.roundUp(10) should equal(10) }
  
  // original spec:
  test("X=10, Y=85, D=30 -> 3") {
    Solution.solution(x = 10, y = 85, d = 30) should equal(3)
  }
}
(new SolutionSpec).execute
