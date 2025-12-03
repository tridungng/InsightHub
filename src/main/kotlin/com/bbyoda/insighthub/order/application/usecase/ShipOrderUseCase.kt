package com.bbyoda.insighthub.order.application.usecase

import com.bbyoda.insighthub.order.application.dto.OrderDto
import com.bbyoda.insighthub.order.application.dto.OrderError
import com.bbyoda.insighthub.order.application.port.EventPublisher
import com.bbyoda.insighthub.order.domain.repository.OrderRepository
import com.bbyoda.insighthub.shared.kernel.Result

class ShipOrderUseCase(
    private val orders: OrderRepository,
    private val events: EventPublisher
) {
    data class Cmd(val orderId: String, val trackingNumber: String)

    fun execute(cmd: Cmd): Result<OrderDto, OrderError> {
        val order = orders.findById(cmd.orderId) ?: return Result.failure(OrderError.NotFound)
        order.startFulfillment()
        order.markShipped(cmd.trackingNumber)
        val saved = orders.save(order)
        events.publish(saved.domainEvents)
        saved.clearDomainEvents()
        return Result.success(OrderDto.fromDomain(saved))
    }
}