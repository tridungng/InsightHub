package com.bbyoda.insighthub.identity.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent
import com.bbyoda.insighthub.shared.types.UserId

class UserCreated(
    val userId: UserId,
    val email: String,
    val firstName: String,
    val lastName: String,
) : DomainEvent