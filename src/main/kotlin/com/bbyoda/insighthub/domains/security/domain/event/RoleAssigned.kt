package com.bbyoda.insighthub.domains.security.domain.event

import com.bbyoda.insighthub.shared.kernel.DomainEvent
import com.bbyoda.insighthub.shared.types.UserId

data class RoleAssigned(
    val userId: UserId,
    val roleName: String,
) : DomainEvent