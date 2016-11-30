package shop

case class Item(name: String, cost: BigDecimal)

case class SpecialOffer(itemName: String, f: List[Item] => List[Item])

object Shop {

  def bogof(items: List[Item]): List[Item] = items match {
    case i1 :: i2 :: is => i1 :: bogof(is)
    case is => is
  }

  def threeForTwo(items: List[Item]): List[Item] = items match {
    case i1 :: i2 :: i3 :: is => i1 :: i2 :: threeForTwo(is)
    case is => is
  }

  def applySpecialOffers(offers: Map[String, List[SpecialOffer]], items: Map[String, List[Item]]): List[Item] =
    items.map { case (k, v) => k -> offers.getOrElse(k, Nil).foldLeft(v)((is, so) => so.f(is))}.values.flatten.toList

}

case class Shop(stock: List[Item], specialOffers: List[SpecialOffer] = Nil) {

  import Shop._

  val specialOffersByItemName: Map[String, List[SpecialOffer]] = specialOffers.groupBy(_.itemName)

  // sum 'items' by 'cost' - NYI
  def checkOut(items: List[Item]): BigDecimal = applySpecialOffers(specialOffersByItemName, items.groupBy(_.name)).map(_.cost).fold(BigDecimal(0))(_ + _)

  val stockByName: Map[String, Item] = stock.foldLeft(Map.empty[String, Item]){ (acc, item) => acc + (item.name -> item)}

  // resolve names to items, returning tuple of checkOut(items) and unresolved names
  def checkOutByName(names: List[String]): (BigDecimal, List[String]) = {
    val (is, ns) = names.
      map(name => stockByName.get(name).fold[Either[Item, String]](Right(name))(item => Left(item))).
      foldLeft[(List[Item], List[String])]((Nil, Nil))((acc, e) => {
        val (iacc, nacc) = acc
        e.fold(i => (i :: iacc, nacc), n => (iacc, n :: nacc))
      })
    (checkOut(is), ns.reverse)
  }
}
