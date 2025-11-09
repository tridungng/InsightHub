package com.bbyoda.insighthub.domains.catalog.domain.events

import java.util.UUID

class StockUpdatedEvent(val id: UUID, val newStock: Int)