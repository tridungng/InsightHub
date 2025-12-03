package com.bbyoda.insighthub.order.infrastructure.persistence

import com.bbyoda.insighthub.order.domain.model.OrderStatus
import com.bbyoda.insighthub.order.domain.model.PaymentStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "order_headers")
class OrderEntity(
    @Id
    val id: String,

    @Column(nullable = false, name = "user_id")
    val userId: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    var status: OrderStatus,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_status")
    var paymentStatus: PaymentStatus,

    @Column(nullable = false, name = "created_at")
    val createdAt: Instant
) {
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var items: MutableList<OrderItemEntity> = mutableListOf()
}