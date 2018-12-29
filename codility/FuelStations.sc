case class Slot(var value: Int, var busy: Boolean, index: Int) {
    def satisfy(demand: Int) = { value = value - demand; busy = true }
    def releaseSlot = { busy = false }
    override def toString = ":" + value + (if (busy) "*" else "")
}

case class Station(var x: Int, var y: Int, var z: Int) {
  var slots: Array[Slot] = Array(
    Slot(x, false, 0), Slot(y, false, 1), Slot(z, false, 2)
  )
  def hasDemandFor(demand: Int): Boolean = {
    slots.filter(x => (x.busy == false && x.value >= demand)).length > 0
  }
  def nextSlotIndex(demand: Int): Int = {
    slots.filter(x => (x.busy == false && x.value >= demand)).map(_.index).head
  }
   // returns how many
  def satisfy(next: Array[Int]): Int = {
    var taken = 0
    // println(" CHECKING", next.toList)
    if (next.length > 0) {
        for (carIndex <- 0 until next.length) {
            val carDemand = next(carIndex)
            // println(" supply for ", carDemand, "in ", slots.toList)
            if (hasDemandFor(carDemand)) {
                // println(" carIndex =", carIndex, "carDemand=", carDemand, "slots=", slots.toList)
                val nextIndex = nextSlotIndex(carDemand)
                slots(nextIndex).satisfy(carDemand)
                // println(" NEXTINDEX =", nextIndex, " SLOTS: ", slots.toList)
                taken += 1 
                println("  .["+carIndex+"] satisfied " + carDemand + " at index " + nextIndex + " ", slots.toList)
            } else {
                // println(" no supply for ", carDemand, "SLOTS: ",  slots.toList)
                println("  .["+carIndex+"] not satisfied " + carDemand + " ", slots.toList)
            }
        }
    }
    slots.foreach(_.releaseSlot)
    taken
  }
}

object Solution { 
  def solution(a: Array[Int], x: Int, y: Int, z: Int): Int = {
    val station = Station(x, y, z)
    var steps = 0
    var isBlocked = false
    var index = 0
    println("--- INPUT --- ", a.toList)
    while (index < a.length && !isBlocked) {
        // at each step, try to send all cars
        val next3 = a.slice(index, index + 3)
        println("1. NEXT FROM " + next3.toList + " SLOTS=" + station.slots.toList)

        val taken = station.satisfy(next3)
        val takenSet = a.slice(index, index + taken)
        if (taken == 0) {
            isBlocked = true
            println("*", " BLOCKED", "SLOTS=", station.slots.toList)
        }  else {
            // println("DONE WITH", a.toList, "next3=", next3.toList, "taken=", taken)
            steps += 1
            index += taken
        }
        println("2. TAKEN=", takenSet.toList, "REMAIN=", a.slice(index, index + 3).toList, "SLOTS=", station.slots.toList)
    }
    if (isBlocked) -1 else steps
  }
}

import $ivy.`org.scalatest::scalatest:3.0.5`
import org.scalatest._
class SolutionSpec extends FunSuite with Matchers {
  // original spec:
  test("[2,8,4,3,2] x=7, y=11, z=3 -> 8") {
    Solution.solution(Array[Int](2,8,4,3,2), x=7, y=11, z=3) should equal(8)
  }
  test("[5] x=4, y=0, z=3 -> -1") {
    Solution.solution(Array[Int](5), x=4, y=0, z=3) should equal(-1)
  }
}
(new SolutionSpec).execute
