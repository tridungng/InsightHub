package com.bbyoda.insighthub.order.infrastructure.persistence

import com.bbyoda.insighthub.order.domain.model.Order
import com.bbyoda.insighthub.order.domain.repository.OrderRepository
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val jpa: OrderJpaRepository,
    private val mapper: OrderMapper
) : OrderRepository {

    override fun save(order: Order): Order = mapper.toDomain(jpa.save(mapper.toEntity(order)))

    override fun findById(id: String): Order? = jpa.findById(id).orElse(null)?.let(mapper::toDomain)

    override fun findByUserId(userId: String, limit: Int): List<Order> =
        jpa.findByUserIdOrderByCreatedAtDesc(userId).take(limit).map(mapper::toDomain)
}
