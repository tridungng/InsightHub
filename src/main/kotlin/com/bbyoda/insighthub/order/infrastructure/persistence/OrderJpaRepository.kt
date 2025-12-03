package com.bbyoda.insighthub.order.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository : JpaRepository<OrderEntity, String> {
    fun findByUserIdOrderByCreatedAtDesc(userId: String): List<OrderEntity>
}