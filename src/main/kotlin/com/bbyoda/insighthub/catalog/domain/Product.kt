package com.bbyoda.insighthub.catalog.domain

import java.math.BigDecimal
import java.util.UUID


class Product (
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var description: String,
    var price: BigDecimal,
    var stock: Int,
    var category: Category
    )