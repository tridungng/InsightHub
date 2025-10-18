package com.bbyoda.insighthub.catalog.domain.events

import java.math.BigDecimal
import java.util.UUID

class ProductCreatedEvent(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val stock: Int,
)