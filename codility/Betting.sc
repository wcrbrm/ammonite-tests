// one or two ways, each game:
// 1. betting one chip 
// 2. betting all in

// equal wage, if he bets C chips and wins, 
// he gets 2C chips back
// if loses, no chips back

// yesterday: started with 1 chip and left with N chips
// he played all in no more than K times

// WRITE A FUNCTION
// minimum number of rounds that are necessary for John to leave the casino
// with N chips, having played all-in no more than K times

// Write an efficient algorithm
object Solution { 
   def solution(n:Int, k:Int): Int = {
      if (k == 0) {
        n - 1
      } else {
        // println("=== checking N=" + n + ", K=" + k)
        var next = n
        var stepsAllIn = 0
        var remained = 0
        while (next >= 2 && stepsAllIn < k) {
            val prev = next
            next = (next / 2).toInt
            remained += prev - next * 2
            // println(">> next=" + next + " prev=" + prev + " remained=" + remained)
            stepsAllIn += 1
        }
        // println("** stepsAllIn=" + stepsAllIn + " next=" + next + " remained=" + remained)
        stepsAllIn + next + remained - 1
      }
   }
}

import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {
  // original spec:
  test("N=8, K=0 -> 7") {
    Solution.solution(8, 0) should equal(7)
  }
  test("N=18, K=2 -> 6") {
    Solution.solution(18, 2) should equal(6)
  }
  test("N=10, K=10 -> 4") {
    Solution.solution(10, 10) should equal(4)
  }
  
}
(new SolutionSpec).execute
