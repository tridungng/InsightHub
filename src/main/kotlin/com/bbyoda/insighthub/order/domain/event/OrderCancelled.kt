package com.bbyoda.insighthub.order.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent
import java.time.Instant

data class OrderCancelled(
    val orderId: String,
    val reason: String,
    override val occurredAt: Instant = Instant.now()
) : DomainEvent