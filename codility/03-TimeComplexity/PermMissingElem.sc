// An array A consisting of N different integers is given. 
// The array contains integers in the range [1..(N + 1)], 
// which means that exactly one element is missing.

// Your goal is to find that missing element.
// Write a function that, given an array A, returns the value of the missing element.

// Write an efficient algorithm for the following assumptions:

// N is an integer within the range [0..100,000];
// the elements of A are all distinct;
// each element of array A is an integer within the range [1..(N + 1)].

object Solution { 
  def viaSum(a: Array[Int]): Int  = {
    val n = a.length + 1
    val expected = n * (n + 1) / 2
    val actual = a.reduce(_ + _)
    expected - actual
  }
  def viaXor(a: Array[Int]): Int  = {
    val n = a.length + 1
    val expected = (1 to n).reduce( _ ^ _)
    val actual = a.reduce(_ ^ _)
    Math.abs(expected - actual)
  }
  def solution(a: Array[Int]): Int = viaXor(a)
}

import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {

  test("(1,3) -> 2") {
    Solution.solution(Array(1, 3)) should equal(2)
  }
  test("(1,4,3) -> 2") {
    Solution.solution(Array(1, 4, 3)) should equal(2)
  }
  test("(4,1,2) -> 3") {
    Solution.solution(Array(4, 1, 2)) should equal(3)
  }
  test("(2,3,1,5) -> 4") {
    Solution.solution(Array(2, 3, 1, 5)) should equal(4)
  }
}
(new SolutionSpec).execute
