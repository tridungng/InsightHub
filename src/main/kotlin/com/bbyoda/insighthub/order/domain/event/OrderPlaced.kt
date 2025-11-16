package com.bbyoda.insighthub.order.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent
import com.bbyoda.insighthub.shared.types.Money
import java.time.Instant

data class OrderPlaced(
    val orderId: String,
    val userId: String,
    val total: Money,
    override val occurredAt: Instant = Instant.now()
) : DomainEvent