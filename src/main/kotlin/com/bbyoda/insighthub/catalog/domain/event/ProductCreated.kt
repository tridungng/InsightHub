package com.bbyoda.insighthub.catalog.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent
import java.time.Instant

data class ProductCreated(
    val productId: String,
    val name: String,
    override val occurredAt: Instant = Instant.now(),
    val createdAt: Instant = occurredAt
) : DomainEvent