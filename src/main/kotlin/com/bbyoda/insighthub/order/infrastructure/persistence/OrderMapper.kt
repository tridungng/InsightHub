package com.bbyoda.insighthub.order.infrastructure.persistence

import com.bbyoda.insighthub.order.domain.model.Order
import com.bbyoda.insighthub.order.domain.model.OrderItem
import com.bbyoda.insighthub.shared.types.Money
import com.bbyoda.insighthub.shared.types.UserId
import org.springframework.stereotype.Component

@Component
class OrderMapper {

    fun toEntity(domain: Order): OrderEntity {
        val entity = OrderEntity(
            id = domain.id,
            userId = domain.userId.value,
            status = domain.status,
            paymentStatus = domain.paymentStatus,
            createdAt = domain.createdAt
        )

        entity.items = domain.itemsView().map {
            OrderItemEntity(
                order = entity,
                productId = it.productId,
                name = it.name,
                unitAmount = it.unitPrice.amount,
                currency = it.unitPrice.currency,
                quantity = it.quantity
            )
        }.toMutableList()

        return entity
    }

    fun toDomain(entity: OrderEntity): Order {
        val order = Order(
            id = entity.id,
            userId = UserId(entity.userId),
            status = entity.status,
            paymentStatus = entity.paymentStatus,
            createdAt = entity.createdAt,
            items = entity.items.map {
                OrderItem(
                    productId = it.productId,
                    name = it.name,
                    unitPrice = Money(it.unitAmount, it.currency),
                    quantity = it.quantity
                )
            }.toMutableList()
        )
        return order
    }
}