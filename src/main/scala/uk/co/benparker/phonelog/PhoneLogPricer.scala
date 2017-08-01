package uk.co.benparker.phonelog

object PhoneLogPricer {

  def price(log: String) : Int = {
    val logEntries = log.lines.map {
      _.split(',') match {
        case Array(phoneNo, timeString) => phoneNo -> toSeconds(timeString)
      }
    } toSeq

    val calls : Map[String, List[Int]] = logEntries groupBy (_._1) mapValues (_ map {_._2} toList)

    val discountedCalls = removeMostCalled(calls)
    discountedCalls.values.flatten.foldLeft(0)(_ + priceCalls(_))
  }

  private def removeMostCalled(calls: Map[String, List[Int]]) : Map[String, List[Int]] = {
    if (calls.size > 1) {
      val (mostCalled, _) = calls.maxBy(_._2.length)
      calls - mostCalled
    } else {
      calls
    }
  }

  private def priceCalls(seconds : Int) : Int = {
    if (seconds < 60 * 5) {
      seconds * 3
    } else {
      toPartialMinutes(seconds) * 150
    }
  }

  private def toPartialMinutes(seconds: Int) : Int = (seconds / 60.0).ceil.toInt

  private def toSeconds(time: String) = {
    val Array(h, m, s) = time.split(':').map(_.toInt)
    h * 60 * 60 + m * 60 + s
  }
}
