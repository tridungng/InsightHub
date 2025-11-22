package com.bbyoda.insighthub.order.application

import com.bbyoda.insighthub.order.application.dto.OrderDto
import com.bbyoda.insighthub.order.application.dto.OrderError
import com.bbyoda.insighthub.order.application.port.EventPublisher
import com.bbyoda.insighthub.order.application.port.InventoryPort
import com.bbyoda.insighthub.order.domain.model.Order
import com.bbyoda.insighthub.order.domain.model.OrderItem
import com.bbyoda.insighthub.order.domain.repository.OrderRepository
import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.shared.types.Money
import com.bbyoda.insighthub.shared.types.UserId

class PlaceOrderUseCase(
    private val orders: OrderRepository,
    private val inventory: InventoryPort,
    private val events: EventPublisher
) {
    data class Cmd(
        val userId: String,
        val items: List<Item>
    ) {
        data class Item(
            val productId: String,
            val name: String,
            val priceAmount: Double,
            val currency: String,
            val quantity: Int
        )
    }

    fun execute(cmd: Cmd): Result<OrderDto, OrderError> {
        if (cmd.items.isEmpty()) return Result.failure(OrderError.EmptyOrder)


        val order = Order(userId = UserId(cmd.userId))
        cmd.items.forEach { item ->
            order.addItem(
                OrderItem(
                    item.productId,
                    item.name,
                    Money.of(item.priceAmount, item.currency),
                    item.quantity
                )
            )
        }

        order.place()

        val reserved = inventory.reserve(
            order.id,
            order.itemsView().map { InventoryPort.Line(it.productId, it.quantity) }
        )
        if (reserved) order.markReserved() else order.markReservationFailed("Reservation failed")

        val saved = orders.save(order)
        events.publish(saved.domainEvents)
        saved.clearDomainEvents()

        return Result.success(OrderDto.fromDomain(saved))
    }
}