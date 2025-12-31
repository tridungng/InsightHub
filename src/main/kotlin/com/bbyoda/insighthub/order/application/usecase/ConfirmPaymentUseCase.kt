package com.bbyoda.insighthub.order.application.usecase

import org.springframework.stereotype.Service

import com.bbyoda.insighthub.order.application.dto.OrderDto
import com.bbyoda.insighthub.order.application.dto.OrderError
import com.bbyoda.insighthub.order.application.port.EventPublisher
import com.bbyoda.insighthub.order.application.port.PaymentPort
import com.bbyoda.insighthub.order.domain.repository.OrderRepository
import com.bbyoda.insighthub.shared.kernel.Result

@Service
class ConfirmPaymentUseCase(
    private val orders: OrderRepository,
    private val payments: PaymentPort,
    private val events: EventPublisher
) {
    data class Cmd(val orderId: String, val customerId: String)

    fun execute(cmd: Cmd): Result<OrderDto, OrderError> {
        val order = orders.findById(cmd.orderId) ?: return Result.failure(OrderError.NotFound)

        val charge = payments.charge(order.id, order.total(), cmd.customerId)
        if (!charge.success || charge.transactionId == null) {
            return Result.failure(OrderError.PaymentFailed(charge.failureReason ?: "Unknown"))
        }

        order.markPaid(charge.transactionId)
        val saved = orders.save(order)
        events.publish(saved.domainEvents)
        saved.clearDomainEvents()

        return Result.success(OrderDto.fromDomain(saved))
    }
}
