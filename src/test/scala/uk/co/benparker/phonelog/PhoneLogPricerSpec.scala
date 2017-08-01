package uk.co.benparker.phonelog

import org.scalatest.{MustMatchers, WordSpec}

class PhoneLogPricerSpec extends WordSpec with MustMatchers {

  "Phone Log" must {

    "price single entry less than 5 minutes" in {
      val log = "456-123-101,00:01:07"
      PhoneLogPricer.price(log) mustEqual 67 * 3
    }

    "price single entry equal to 5 minutes" in {
      val log = "456-123-101,00:05:00"
      PhoneLogPricer.price(log) mustEqual 5 * 150
    }

    "price for partial minutes for entry more than 5 minutes" in {
      val log = "456-123-101,00:06:19"
      PhoneLogPricer.price(log) mustEqual 7 * 150
    }

    "price multiple entries" in {
      val log = "456-123-101,00:01:07\n456-123-101,00:06:19"
      PhoneLogPricer.price(log) mustEqual (67 * 3) + (7 * 150)
    }

    "price multiple entries for different phone nos with most common number as free calls" in {
      val log = "456-123-101,00:01:07\n456-123-102,00:05:00\n456-123-101,00:06:19\n456-123-103,01:02:43"
      PhoneLogPricer.price(log) mustEqual (5 * 150) + (63 * 150)
    }
  }
}
