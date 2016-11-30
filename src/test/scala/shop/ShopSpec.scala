package shop

import org.scalatest.FlatSpec
import org.scalactic.TypeCheckedTripleEquals

class ShopSpec extends FlatSpec with TypeCheckedTripleEquals {

  val apple  = Item("Apple",  BigDecimal(0.60))
  val orange = Item("Orange", BigDecimal(0.25))

  val stock = List(apple, orange)

  "checkout()" should "sum a list of items" in {
    val shop = Shop(stock)
    assert(shop.checkOut(apple :: apple :: orange :: apple :: Nil) === BigDecimal(2.05), "checkout failed")
  }

  "checkoutByName()" should "return the checkout() of resolvable names and a list of unresolvable names" in {
    val shop = Shop(stock)
    assert(shop.checkOutByName("Apple" :: "Apple" :: "Lemon" :: "Orange" :: "Apple" :: "Lime" :: Nil) === (BigDecimal(2.05), List("Lemon", "Lime")), "checkoutByName failed")
  }

  import Shop._

  "buy-one-get-one-free" should "reduce items accordingly" in {
    assert(bogof(List()).length === 0)
    assert(bogof(List(apple)).length === 1)
    assert(bogof(List(apple, apple)).length === 1)
    assert(bogof(List(apple, apple, apple)).length === 2)
    assert(bogof(List(apple, apple, apple, apple)).length === 2)
    assert(bogof(List(apple, apple, apple, apple, apple)).length === 3)
  }

  "three-for-the-price-of-two" should "reduce items accordingly" in {
    assert(threeForTwo(List()).length === 0)
    assert(threeForTwo(List(orange)).length === 1)
    assert(threeForTwo(List(orange, orange)).length === 2)
    assert(threeForTwo(List(orange, orange, orange)).length === 2)
    assert(threeForTwo(List(orange, orange, orange, orange)).length === 3)
    assert(threeForTwo(List(orange, orange, orange, orange, orange)).length === 4)
    assert(threeForTwo(List(orange, orange, orange, orange, orange, orange)).length === 4)
    assert(threeForTwo(List(orange, orange, orange, orange, orange, orange, orange)).length === 5)
  }

  "special offers" should "be applied on checkout " in {
    val specialOffers = List(SpecialOffer("Apple", bogof), SpecialOffer("Orange", threeForTwo))
    val shop = Shop(stock, specialOffers)

    assert(shop.checkOut(apple :: apple :: Nil) === BigDecimal(0.60), "checkout with special offers failed")
    assert(shop.checkOut(orange :: orange :: orange :: Nil) === BigDecimal(0.50), "checkout with special offers failed")
    assert(shop.checkOut(orange :: orange :: apple :: orange :: apple :: Nil) === BigDecimal(1.10), "checkout with special offers failed")
  }

}
