package com.bbyoda.insighthub.order.application.usecase

import org.springframework.stereotype.Service

import com.bbyoda.insighthub.order.application.dto.OrderDto
import com.bbyoda.insighthub.order.application.dto.OrderError
import com.bbyoda.insighthub.order.domain.repository.OrderRepository
import com.bbyoda.insighthub.shared.kernel.Result

@Service
class GetOrderUseCase(private val orders: OrderRepository) {
    fun byId(id: String): Result<OrderDto, OrderError> {
        val order = orders.findById(id) ?: return Result.failure(OrderError.NotFound)
        return Result.success(OrderDto.fromDomain(order))
    }
}