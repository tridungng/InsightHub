package com.bbyoda.insighthub.order.application.usecase

import com.bbyoda.insighthub.order.application.dto.OrderDto
import com.bbyoda.insighthub.order.application.dto.OrderError
import com.bbyoda.insighthub.order.application.port.EventPublisher
import com.bbyoda.insighthub.order.domain.repository.OrderRepository
import com.bbyoda.insighthub.shared.kernel.Result
import org.springframework.stereotype.Service

@Service
class CompleteOrderUseCase(
    private val orders: OrderRepository,
    private val events: EventPublisher
) {
    fun execute(orderId: String): Result<OrderDto, OrderError> {
        val order = orders.findById(orderId) ?: return Result.failure(OrderError.NotFound)
        order.complete()
        val saved = orders.save(order)
        events.publish(saved.domainEvents)
        saved.clearDomainEvents()
        return Result.success(OrderDto.fromDomain(saved))
    }
}