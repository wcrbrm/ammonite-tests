
// that, given an array A of N integers, returns the smallest positive integer 
// (greater than 0) that does not occur in A.

// For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.

// Write an efficient algorithm for the following assumptions:
// N is an integer within the range [1..100,000];
// each element of array A is an integer within the range [−1,000,000..1,000,000].

object Solution { 
   def solution(a: Array[Int]): Int = {
      val positive = a.filter(x => x > 0 && x <= a.length)
      val len = positive.length

      var nextval = 0
      for (index <- 1 until len) {
        var v = positive(index)
        // traverse the array until we reach at an element 
        // which is already marked or which could not be marked. 
        var continue = true
        while ( continue && positive(v - 1) != v ) { 
            nextval = positive(v - 1) 
            positive(v - 1) = v
            v = nextval
            if (v < 0 || v >= len) continue = false
        } 
      } 
      
      // find first array index which is 
      // not marked which is also the smallest positive missing number.
      val next = (1 to len).filter({
            index => index > 0 && index < len && positive(index) != (index + 1)
      }).map(_ + 1)

      println(a.toList, " -> ", positive.toList, "nextval=" + nextval, "next=" + next)
      next.headOption.getOrElse(len + 1)
   }
}


import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {
  // original spec:
  test("[1, 3, 6, 4, 1, 2] -> 5") {
    Solution.solution(Array[Int](1, 3, 6, 4, 1, 2)) should equal(5)
  }
  test("[1, 2, 3] -> 4") {
    Solution.solution(Array[Int](1, 2, 3)) should equal(4)
  }
  test("[-1, -3] -> 1") {
     Solution.solution(Array[Int](-1,-3)) should equal(1)
  }
  test("[0, 10, 2, -10, -20] -> 2") {
     Solution.solution(Array[Int](0, 10, 2, -10, -20)) should equal(2)
  }
  test("[4, 5, 6, 2] -> 1") {
    Solution.solution(Array[Int](4, 5, 6, 2)) should equal(1)
  }
  // 2, 3, 7, 6, 8, -1, -10, 15 -> 1
  // 2, 3, -7, 6, 8, 1, -10, 15 -> 4
  // 1, 1, 0, -1, -2
}
(new SolutionSpec).execute
