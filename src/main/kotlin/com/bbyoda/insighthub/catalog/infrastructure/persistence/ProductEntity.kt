package com.bbyoda.insighthub.catalog.infrastructure.persistence

import com.bbyoda.insighthub.catalog.domain.model.ProductStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "products")
class ProductEntity(
    @Id
    val id: String,

    @Column(nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(nullable = false, name = "price_amount")
    var priceAmount: BigDecimal,

    @Column(nullable = false, name = "price_currency")
    var priceCurrency: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ProductStatus,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false, name = "created_at")
    val createdAt: Instant,

    @Column(nullable = false, name = "updated_at")
    var updatedAt: Instant
) {
    constructor() : this(
        id = "",
        name = "",
        description = null,
        priceAmount = BigDecimal.ZERO,
        priceCurrency = "",
        status = ProductStatus.INACTIVE,
        quantity = 0,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_categories", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "category_id")
    var categoryIds: MutableSet<String> = mutableSetOf()
}
