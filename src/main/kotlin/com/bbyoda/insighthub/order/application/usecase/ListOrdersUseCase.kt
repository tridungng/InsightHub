package com.bbyoda.insighthub.order.application.usecase

import org.springframework.stereotype.Service

import com.bbyoda.insighthub.order.application.dto.OrderDto
import com.bbyoda.insighthub.order.domain.repository.OrderRepository

@Service
class ListOrdersUseCase(private val orders: OrderRepository) {
    fun byUser(userId: String, limit: Int = 50): List<OrderDto> =
        orders.findByUserId(userId, limit).map(OrderDto::fromDomain)
}