// A binary gap within a positive integer N is 
// any maximal sequence of consecutive zeros that 
// is surrounded by ones at both ends in the binary 
// representation of N.

object Solution {
  // we do not care about sequences of zero at the beginning or at the end
  def trim0(x: String): String = x.replaceFirst("^0+", "").replaceFirst("0+$", "")
    
  def solution(n: Int): Int = {
    var maxlen: Int = 0
    var len: Int = 0
	trim0(Integer.toString(n, 2)).foreach {
      case '0' => { 
        len += 1
        if (maxlen < len) maxlen = len
      }
      case _ => {
        len = 0
      }
    }
    maxlen
  }
}

import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {
  def num(x:Int): Int = Solution.solution(x)
  def str(x:String): Int = Solution.solution(Integer.parseInt(x, 2))

  // original spec:
  test("1041 (numeric)") { num(1041) should equal(5) }
  test("1041 (binary)") { str("10000010001") should equal(5) }
  test("32 (numeric)") { num(32) should equal(0) }
  test("32 (binary)") { str("100000") should equal(0) }

  // testing trimming leading and ending zeros 
  test("trim0-head") { Solution.trim0("0000111") should equal("111") }
  test("trim0-tail") { Solution.trim0("000100001000") should equal("100001") }
  test("trim0-both") { Solution.trim0("00010101000") should equal("10101") }
  test("trim0-32") { Solution.trim0("100000") should equal("1") }
  test("trim0-only0") { Solution.trim0("000") should equal("") }
  test("trim0-empty") { Solution.trim0("") should equal("") }
}
(new SolutionSpec).execute
