package orders

data class Order(val id: Long, val price: Double, val side: Char, val size: Long)

class OrderBook(val orders: LinkedHashMap<Long, Order>) {

    fun addOrder(order: Order) {
        orders[order.id] = order
    }

    fun removeOrder(orderId: Long) = orders.remove(orderId)

    fun resizeOrder(orderId: Long, updatedSize: Long) =
        orders[orderId]?.let { orders.replace(orderId, it.copy(size = updatedSize)) }
}
