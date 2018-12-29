
// There are N ropes numbered from 0 to N − 1, whose lengths 
// are given in an array A, lying on the floor in a line. 
// For each I (0 ≤ I < N), the length of rope I on the line is A[I].

// We say that two ropes I and I + 1 are adjacent. Two adjacent ropes 
// can be tied together with a knot, and the length of the tied rope 
// is the sum of lengths of both ropes. The resulting new rope can then be tied again.

// For a given integer K, the goal is to tie the ropes in such a way
// that the number of ropes whose length is greater than or equal to K is maximal.

// Write a function that, given an integer K and a non-empty array A of N integers,
// returns the maximum number of ropes of length greater than or equal to K 
// that can be created.

object Solution { 
    def solution(k: Int, a: Array[Int]): Int = {
        var sum = 0
        var ropes = 0
        a.foreach(v => {
            if (v >= k || (v + sum) >= k) {
                ropes += 1
                sum = 0
            } else {
                sum += v
            }
            // println(v, " sum=" +  sum, " ropes=" + ropes)            
        })
        ropes
    }
}


import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {
  // original spec:
  test("original K=4 (1, 2, 3, 4, 1, 1, 3) -> 3") {
    Solution.solution(4, Array[Int](1, 2, 3, 4, 1, 1, 3)) should equal(3)
  }
  test("K=3 (1,1,1,2,2,2) -> 2") {
     Solution.solution(3, Array[Int](1, 1, 1, 2, 2, 2)) should equal(2)
  }
}
(new SolutionSpec).execute
