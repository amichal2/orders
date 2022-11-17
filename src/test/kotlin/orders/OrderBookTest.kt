package orders

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class OrderBookTest {

    private var orderBook = OrderBook(LinkedHashMap())

    @BeforeEach
    internal fun setUp() {
        orderBook = OrderBook(linkedMapOf(
            542989L to Order(542989L, 162.01, 'O', 9),
            18347L to Order(18347L, 123.92, 'O', 4),
            947332L to Order(947332L, 85.01, 'B', 1),
            9387891L to Order(9387891L, 133.03, 'O', 21),
            845L to Order(845L, 93.92, 'B', 33),
            8347567L to Order(8347567L, 162.01, 'O', 17),
            23890L to Order(23890L, 85.01, 'B', 1004),
            377915L to Order(377915L, 5.04, 'B', 85),
            373452L to Order(373452L, 62.92, 'B', 55),
            8983775L to Order(8983775L, 41.06, 'B', 2),
            4552901L to Order(4552901L, 112.05, 'O', 1),
            8733L to Order(8733L, 22.07, 'B', 76),
            18903L to Order(18903L, 102.14, 'O', 45),
            434866L to Order(434866L, 85.01, 'B', 8),
            7783L to Order(7783L, 102.14, 'O', 91),
            434498L to Order(434498L, 133.03, 'O', 1234),
            3447615L to Order(3447615L, 102.14, 'O', 91),
            743567L to Order(743567L, 102.14, 'O', 54),
            13396L to Order(13396L, 39.92, 'B', 41),
            788L to Order(788L, 62.92, 'B', 14),
        ))
    }

    @Test
    internal fun `adds an order`() {
        val orderId = 111L
        assertFalse(orderBook.orders.contains(orderId))

        orderBook.addOrder(Order(orderId, 2.01, 'B', 25L))
        assertTrue(orderBook.orders.contains(orderId))
    }

    @Test
    internal fun `removes an order`() {
        val orderId = 542989L
        assertTrue(orderBook.orders.contains(orderId))

        orderBook.removeOrder(orderId)
        assertFalse(orderBook.orders.contains(orderId))
    }

    @Test
    internal fun `resizes an order`() {
        val orderId = 542989L
        val updatedSize = 1500L
        assertTrue(orderBook.orders.contains(orderId))
        assertNotEquals(updatedSize, orderBook.orders[orderId]!!.size)  //check for object presence in first assertion

        orderBook.resizeOrder(orderId, updatedSize)
        assertEquals(updatedSize, orderBook.orders[orderId]!!.size)     //check for object presence in first assertion
    }

    @ParameterizedTest
    @CsvSource(
        "102.14, 'O', 1",
        "112.05, 'O', 2",
        "123.92, 'O', 3",
        "133.03, 'O', 4",
        "93.92, 'B', 1",
        "85.01, 'B', 2",
        "62.92, 'B', 3",
        "41.06, 'B', 4",
    )
    internal fun `retrieves price from given level`(expectedPrice: Double, side: Char, level: Int) {
        assertEquals(expectedPrice, orderBook.retrievePriceFromLevel(side, level))
    }

    @ParameterizedTest
    @CsvSource(
        "281, 'O', 1",
        "1, 'O', 2",
        "4, 'O', 3",
        "1255, 'O', 4",
        "33, 'B', 1",
        "1013, 'B', 2",
        "69, 'B', 3",
        "2, 'B', 4",
    )
    internal fun `retrieves total size from given level`(expectedSize: Long, side: Char, level: Int) {
        assertEquals(expectedSize, orderBook.retrieveTotalSizeFromLevel(side, level))
    }

    @Test
    internal fun `retrieves orders from given side`() {
        val bids = listOf(
            Order(845L, 93.92, 'B', 33),
            Order(947332, 85.01, 'B', 1),
            Order(23890, 85.01, 'B', 1004),
            Order(434866, 85.01, 'B', 8),
            Order(373452, 62.92, 'B', 55),
            Order(788, 62.92, 'B', 14),
            Order(8983775, 41.06, 'B', 2),
            Order(13396, 39.92, 'B', 41),
            Order(8733, 22.07, 'B', 76),
            Order(377915, 5.04, 'B', 85)
        )

        assertThat(bids).containsExactlyElementsOf(orderBook.retrieveOrdersFromSide('B'))

        val offers = listOf(
            Order(18903, 102.14, 'O', 45),
            Order(7783, 102.14, 'O', 91),
            Order(3447615, 102.14, 'O', 91),
            Order(743567, 102.14, 'O', 54),
            Order(4552901, 112.05, 'O', 1),
            Order(18347, 123.92, 'O', 4),
            Order(9387891, 133.03, 'O', 21),
            Order(434498, 133.03, 'O', 1234),
            Order(542989, 162.01, 'O', 9),
            Order(8347567, 162.01, 'O', 17)
        )

        assertThat(offers).containsExactlyElementsOf(orderBook.retrieveOrdersFromSide('O'))
    }
}
