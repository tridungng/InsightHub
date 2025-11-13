package com.bbyoda.insighthub.domains.security.domain.model

data class Role(
    val name: String,
    val permissions: Set<Permission> = emptySet()
)