package com.bbyoda.insighthub.catalog.domain.events

import java.util.UUID

class StockUpdatedEvent(val id: UUID, val newStock: Int)