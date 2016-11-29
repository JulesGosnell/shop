package shop

import org.scalatest.FlatSpec
import org.scalactic.TypeCheckedTripleEquals

class ShopSpec extends FlatSpec with TypeCheckedTripleEquals {

  val apple  = Item("Apple",  BigDecimal(0.60))
  val orange = Item("Orange", BigDecimal(0.25))

  "checkout()" should "sum a list of items" in {
    val shop = new Shop()
    assert(shop.checkOut(apple :: apple :: orange :: apple :: Nil) == BigDecimal(2.05), "checkout failed")
  }

}
