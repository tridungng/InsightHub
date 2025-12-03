package com.bbyoda.insighthub.order.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
class OrderItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: OrderEntity,

    @Column(nullable = false, name = "product_id")
    val productId: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, name = "unit_amount")
    val unitAmount: BigDecimal,

    @Column(nullable = false, name = "currency")
    val currency: String,

    @Column(nullable = false)
    val quantity: Int
)