package com.bbyoda.insighthub.catalog.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "categories")
class Category(
    @Id val id: UUID = UUID.randomUUID(),
    var name: String,
    var description: String,
)