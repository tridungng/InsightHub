package com.bbyoda.insighthub.catalog.domain

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "products")
class Product(
    @Id val id: UUID = UUID.randomUUID(),
    var name: String,
    var description: String,
    var price: BigDecimal,
    var stock: Int,
    @ManyToOne(fetch = FetchType.LAZY) var category: Category?
)