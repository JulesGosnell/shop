package shop

// let's represent items by composition, rather than heading straight for a sealed trait...

case class Item(name: String, cost: BigDecimal)

case class Shop(stock: List[Item]) {

  // sum 'items' by 'cost' - NYI
  def checkOut(items: List[Item]): BigDecimal = items.map(_.cost).fold(BigDecimal(0))(_ + _)

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
