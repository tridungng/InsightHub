package com.bbyoda.insighthub.order.domain.model

import com.bbyoda.insighthub.shared.types.Money
import java.math.BigDecimal

data class OrderItem(
    val productId: String,
    val name: String,
    val unitPrice: Money,
    val quantity: Int
) {
    init {
        require(quantity > 0) { "Quantity must be > 0" }
    }

    fun lineTotal(): Money = unitPrice.multiply(BigDecimal(quantity))
}