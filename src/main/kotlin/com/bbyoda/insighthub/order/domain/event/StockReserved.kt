package com.bbyoda.insighthub.order.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent

data class StockReserved(val orderId: String) : DomainEvent