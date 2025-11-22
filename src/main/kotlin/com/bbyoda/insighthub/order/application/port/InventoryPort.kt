package com.bbyoda.insighthub.order.application.port

interface InventoryPort {
    fun reserve(orderId: String, lines: List<Line>): Boolean
    fun release(orderId: String)

    data class Line(val productId: String, val quantity: Int)
}