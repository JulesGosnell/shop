package shop

// let's represent items by composition, rather than heading straight for a sealed trait...

case class Item(name: String, cost: BigDecimal)


class Shop {

  // sum 'items' by 'cost' - NYI
  def checkOut(items: List[Item]): BigDecimal = items.map(_.cost).fold(BigDecimal(0))(_ + _)

}
