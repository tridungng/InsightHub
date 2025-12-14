package com.bbyoda.insighthub.catalog.application.port

import com.bbyoda.insighthub.shared.kernel.DomainEvent

interface EventPublisher {
    fun publish(events: Collection<DomainEvent>)
}
