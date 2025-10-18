package com.bbyoda.insighthub.catalog.domain

import java.util.UUID

class Category(
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var description: String,
)