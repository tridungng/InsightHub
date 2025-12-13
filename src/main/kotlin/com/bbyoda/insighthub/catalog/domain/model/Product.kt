package com.bbyoda.insighthub.catalog.domain.model

import com.bbyoda.insighthub.catalog.domain.event.ProductCreated
import com.bbyoda.insighthub.catalog.domain.event.StockUpdated
import com.bbyoda.insighthub.shared.kernel.AggregateRoot
import java.time.Instant
import java.util.UUID

class Product(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String?,
    var price: Price,
    var status: ProductStatus = ProductStatus.ACTIVE,
    val categoryIds: MutableSet<String> = mutableSetOf(),
    var inventory: Inventory = Inventory(quantity = 0),
    val createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now()
) : AggregateRoot<String>() {

    fun addCategory(categoryId: String) {
        categoryIds.add(categoryId)
        updatedAt = Instant.now()
    }

    fun removeCategory(categoryId: String) {
        categoryIds.remove(categoryId)
        updatedAt = Instant.now()
    }

    fun updateStock(delta: Int) {
        val old = inventory.quantity
        inventory = inventory.withAddedStock(delta)
        updatedAt = Instant.now()
        addDomainEvent(StockUpdated(id, old, inventory.quantity))
    }

    fun activate() {
        status = ProductStatus.ACTIVE
        updatedAt = Instant.now()
    }

    fun deactivate() {
        status = ProductStatus.INACTIVE
        updatedAt = Instant.now()
    }

    companion object {
        fun create(name: String, description: String?, price: Price, categoryIds: Set<String>): Product {
            val product = Product(
                name = name,
                description = description,
                price = price,
                categoryIds = categoryIds.toMutableSet()
            )
            product.addDomainEvent(
                ProductCreated(
                    productId = product.id,
                    name = product.name,
                    createdAt = product.createdAt
                )
            )
            return product
        }
    }
}
