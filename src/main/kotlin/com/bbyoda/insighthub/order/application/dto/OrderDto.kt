package com.bbyoda.insighthub.order.application.dto

import com.bbyoda.insighthub.order.domain.model.Order
import com.bbyoda.insighthub.order.domain.model.OrderItem

data class OrderDto(
    val id: String,
    val userId: String,
    val status: String,
    val paymentStatus: String,
    val total: String,
    val items: List<OrderItemDto>
) {
    companion object {
        fun fromDomain(o: Order): OrderDto = OrderDto(
            id = o.id,
            userId = o.userId.value,
            status = o.status.name,
            paymentStatus = o.paymentStatus.name,
            total = o.total().toString(),
            items = o.itemsView().map { OrderItemDto.fromDomain(it) }
        )
    }
}

data class OrderItemDto(
    val productId: String,
    val name: String,
    val unitPrice: String,
    val quantity: Int,
    val lineTotal: String
) {
    companion object {
        fun fromDomain(i: OrderItem) = OrderItemDto(
            productId = i.productId,
            name = i.name,
            unitPrice = i.unitPrice.toString(),
            quantity = i.quantity,
            lineTotal = i.lineTotal().toString()
        )
    }
}
