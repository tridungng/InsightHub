package com.bbyoda.insighthub.domains.security.application.port

import com.bbyoda.insighthub.shared.kernel.DomainEvent

interface EventPublisher {
    fun publish(events: Collection<DomainEvent>)
}