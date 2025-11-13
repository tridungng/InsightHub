package com.bbyoda.insighthub.identity.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent
import com.bbyoda.insighthub.shared.types.UserId
import java.time.Instant

data class PasswordChanged(
    val userId: UserId,
    override val occurredAt: Instant = Instant.now()
) : DomainEvent