package orders

const val BID = 'B'
const val OFFER = 'O'

data class Order(val id: Long, val price: Double, val side: Char, val size: Long)

class OrderBook(val orders: LinkedHashMap<Long, Order>) {

    fun addOrder(order: Order) {
        orders[order.id] = order
    }

    fun removeOrder(orderId: Long) = orders.remove(orderId)

    fun resizeOrder(orderId: Long, updatedSize: Long) =
        orders[orderId]?.let { orders.replace(orderId, it.copy(size = updatedSize)) }

    fun retrievePriceFromLevel(side: Char, level: Int): Double {
        require(level >= 0) { "Illegal level $level" }
        require(BID == side || OFFER == side) { "Unknown side $side" }

        return orders
            .values
            .filter { it.side == side }
            .groupBy { it.price }
            .keys
            .toSortedSet(if (side == BID) Comparator.reverseOrder() else Comparator.naturalOrder())
            .toList()
            .getOrNull(level - 1) ?: throw IllegalStateException("No price found for side $side and level $level")
    }

    fun retrieveTotalSizeFromLevel(side: Char, level: Int): Long {
        require(level >= 0) { "Illegal level $level" }
        require(BID == side || OFFER == side) { "Unknown side $side" }

        return orders
            .values
            .filter { it.side == side }
            .groupBy { it.price }
            .toSortedMap(if (side == BID) Comparator.reverseOrder() else Comparator.naturalOrder())
            .values
            .toList()
            .getOrNull(level - 1)
            ?.let { it.sumOf { order -> order.size } } ?: throw IllegalStateException("No size found for side $side and level $level")
    }

    fun retrieveOrdersFromSide(side: Char): List<Order> {
        require(BID == side || OFFER == side) { "Unknown side $side" }

        return orders
            .values
            .filter { it.side == side }
            .groupBy { it.price }
            .toSortedMap(if (side == BID) Comparator.reverseOrder() else Comparator.naturalOrder())
            .values
            .flatten()
    }
}
