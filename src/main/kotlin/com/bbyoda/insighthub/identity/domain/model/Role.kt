package com.bbyoda.insighthub.identity.domain.model

data class Role(
    val name: String,
    val permissions: Set<Permission> = emptySet()
)