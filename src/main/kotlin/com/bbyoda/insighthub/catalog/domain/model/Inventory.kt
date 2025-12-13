package com.bbyoda.insighthub.catalog.domain.model

data class Inventory(
    val quantity: Int,
    val reserved: Int = 0
) {
    init {
        require(quantity >= 0) { "Quantity cannot be negative" }
        require(reserved >= 0) { "Reserved cannot be negative" }
        require(reserved <= quantity) { "Reserved cannot exceed quantity" }
    }

    fun available(): Int = quantity - reserved

    fun withAddedStock(delta: Int): Inventory {
        val newQty = quantity + delta
        require(newQty >= 0) { "Resulting quantity cannot be negative" }
        return copy(quantity = newQty)
    }
}