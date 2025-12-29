package com.bbyoda.insighthub.catalog.infrastructure.persistence

import com.bbyoda.insighthub.catalog.domain.model.*
import com.bbyoda.insighthub.shared.types.Money
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ProductMapper {

    fun toEntity(domain: Product): ProductEntity =
        ProductEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            priceAmount = domain.price.value.amount,
            priceCurrency = domain.price.value.currency,
            status = domain.status,
            quantity = domain.inventory.quantity,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        ).apply {
            categoryIds = domain.categoryIds.toMutableSet()
        }

    fun toDomain(entity: ProductEntity): Product =
        Product(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            price = Price(Money(entity.priceAmount.setScale(2, BigDecimal.ROUND_HALF_UP), entity.priceCurrency)),
            status = entity.status,
            categoryIds = entity.categoryIds.toMutableSet(),
            inventory = Inventory(quantity = entity.quantity),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
}
