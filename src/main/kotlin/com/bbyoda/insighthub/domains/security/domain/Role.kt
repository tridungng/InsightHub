package com.bbyoda.insighthub.domains.security.domain

import com.bbyoda.insighthub.domains.security.domain.model.Permission

data class Role(
    val name: String,
    val permissions: Set<Permission> = emptySet()
)