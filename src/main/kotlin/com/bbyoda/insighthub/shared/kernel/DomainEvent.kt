package com.bbyoda.insighthub.shared.kernel

import java.time.Instant

interface DomainEvent {
    val occurredAt: Instant
        get() = Instant.now()
}

abstract class BaseDomainEvent(
    open val aggregateId: String,
    override val occurredAt: Instant = Instant.now()
) : DomainEvent

