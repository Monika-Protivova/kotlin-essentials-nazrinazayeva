package com.motycka.edu.lesson02

val coffeeOrders = mutableMapOf<Int, List<String>>()

fun getPrice(item: String): Double {
    return when (item) {
        ESPRESSO -> ESPRESSO_PRICE
        CAPPUCCINO -> CAPPUCCINO_PRICE
        AMERICANO -> AMERICANO_PRICE
        FLAT_WHITE -> FLAT_WHITE_PRICE
        LATTE -> LATTE_PRICE
        DOUBLE_ESPRESSO -> DOUBLE_ESPRESSO_PRICE
        else -> throw IllegalArgumentException("Unknown item: $item")
    }
}

fun placerOrder(items: List<String>): Int {
    val orderId = coffeeOrders.size
    coffeeOrders[orderId] = items
    return orderId
}

fun payOrder(orderId: Int): Double {
    val items = coffeeOrders[orderId] ?: throw IllegalStateException("Order ID $orderId not found.")
    val total = items.sumOf { getPrice(it) }

    return if (items.size >= 3) {
        total - items.minOf { getPrice(it) }
    } else {
        total
    }
}

fun completeOrder(orderId: Int) {
    if (!coffeeOrders.containsKey(orderId)) {
        throw IllegalStateException("Order ID $orderId not found.")
    }
    coffeeOrders.remove(orderId)
}

fun processOrder(items: List<String>, payment: Double): Double {
    val orderId = placerOrder(items)
    val totalToPay = payOrder(orderId)

    if (payment < totalToPay) {
        throw IllegalStateException("Insufficient payment. Total to pay is $totalToPay, but received $payment.")
    }

    val change = payment - totalToPay
    println("Payment successful. Total paid: $payment, Total to pay: $totalToPay, change: $change")
    completeOrder(orderId)
    return change
}
fun main() {
    processOrder(listOf(ESPRESSO, CAPPUCCINO, AMERICANO), 15.0)
}
