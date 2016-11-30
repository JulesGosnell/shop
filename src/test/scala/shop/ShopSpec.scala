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

  import SpecialOffer._

  "buy-one-get-one-free" should "remove free items from basket" in {
    assert(buyOneGetOneFree(List()).length === 0)
    assert(buyOneGetOneFree(List(apple)).length === 1)
    assert(buyOneGetOneFree(List(apple, apple)).length === 1)
    assert(buyOneGetOneFree(List(apple, apple, apple)).length === 2)
    assert(buyOneGetOneFree(List(apple, apple, apple, apple)).length === 2)
    assert(buyOneGetOneFree(List(apple, apple, apple, apple, apple)).length === 3)
  }

  "three-for-the-price-of-two" should "remove free items from basket" in {
    assert(threeForTwo(List()).length === 0)
    assert(threeForTwo(List(orange)).length === 1)
    assert(threeForTwo(List(orange, orange)).length === 2)
    assert(threeForTwo(List(orange, orange, orange)).length === 2)
    assert(threeForTwo(List(orange, orange, orange, orange)).length === 3)
    assert(threeForTwo(List(orange, orange, orange, orange, orange)).length === 4)
    assert(threeForTwo(List(orange, orange, orange, orange, orange, orange)).length === 4)
    assert(threeForTwo(List(orange, orange, orange, orange, orange, orange, orange)).length === 5)
  }

  val specialOffers = List(SpecialOffer("Apple", buyOneGetOneFree), SpecialOffer("Orange", threeForTwo))

  "special offers" should "be applied on checkOut " in {
    val shop = Shop(stock, specialOffers)

    assert(shop.checkOut(apple :: apple :: Nil) === BigDecimal(0.60), "checkOut with special offers failed")
    assert(shop.checkOut(orange :: orange :: orange :: Nil) === BigDecimal(0.50), "checkOut with special offers failed")
    assert(shop.checkOut(orange :: orange :: apple :: orange :: apple :: Nil) === BigDecimal(1.10), "checkOut with special offers failed")
  }

  "special offers" should "be applied on checkOutByName " in {
    val shop = Shop(stock, specialOffers)

    assert(shop.checkOutByName("Apple" :: "Apple" :: Nil) === (BigDecimal(0.60), Nil), "checkOutByName with special offers failed")
    assert(shop.checkOutByName("Orange" :: "Orange" :: "Orange" :: Nil) === (BigDecimal(0.50), Nil), "checkOutByName with special offers failed")
    assert(shop.checkOutByName("Orange" :: "Orange" :: "Apple" :: "Orange" :: "Apple" :: Nil) === (BigDecimal(1.10), Nil), "checkOutByName with special offers failed")
  }

}
