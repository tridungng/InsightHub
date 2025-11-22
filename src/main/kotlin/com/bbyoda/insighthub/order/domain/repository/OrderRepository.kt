package com.bbyoda.insighthub.order.domain.repository

import com.bbyoda.insighthub.order.domain.model.Order

interface OrderRepository {
    fun save(order: Order): Order
    fun findById(id: String): Order?
    fun findByUserId(userId: String, limit: Int = 50): List<Order>
}