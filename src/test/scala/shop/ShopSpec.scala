package shop

import org.scalatest.FlatSpec
import org.scalactic.TypeCheckedTripleEquals

class ShopSpec extends FlatSpec with TypeCheckedTripleEquals {

  val apple  = Item("Apple",  BigDecimal(0.60))
  val orange = Item("Orange", BigDecimal(0.25))

  val stock = List(apple, orange)
  val shop = Shop(stock)

  "checkout()" should "sum a list of items" in {
    assert(shop.checkOut(apple :: apple :: orange :: apple :: Nil) === BigDecimal(2.05), "checkout failed")
  }

  "checkoutByName()" should "return the checkout() of resolvable names and a list of unresolvable names" in {
    assert(shop.checkOutByName("Apple" :: "Apple" :: "Lemon" :: "Orange" :: "Apple" :: "Lime" :: Nil) === (BigDecimal(2.05), List("Lemon", "Lime")), "checkoutByName failed")
  }

}
