package com.bbyoda.insighthub.order.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent
import java.time.Instant

class OrderShipped(
    val orderId: String,
    trackingNumber: String,
    override val occurredAt: Instant = Instant.now()
) : DomainEvent