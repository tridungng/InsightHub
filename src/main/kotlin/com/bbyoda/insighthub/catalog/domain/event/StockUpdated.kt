package com.bbyoda.insighthub.catalog.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent
import java.time.Instant

data class StockUpdated(
    val productId: String,
    val oldQuantity: Int,
    val newQuantity: Int,
    override val occurredAt: Instant = Instant.now()
) : DomainEvent