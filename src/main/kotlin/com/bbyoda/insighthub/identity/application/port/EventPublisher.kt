package com.bbyoda.insighthub.identity.application.port

import com.bbyoda.insighthub.shared.kernel.DomainEvent

interface EventPublisher {
    fun publish(events: Collection<DomainEvent>)
}