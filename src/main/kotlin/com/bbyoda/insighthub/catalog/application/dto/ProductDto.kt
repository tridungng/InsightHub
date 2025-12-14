package com.bbyoda.insighthub.catalog.application.dto

import com.bbyoda.insighthub.catalog.domain.model.Product

data class ProductDto(
    val id: String,
    val name: String,
    val description: String?,
    val price: String,
    val status: String,
    val categories: Set<String>,
    val quantity: Int,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
        fun fromDomain(p: Product): ProductDto = ProductDto(
            id = p.id,
            name = p.name,
            description = p.description,
            price = p.price.toString(),
            status = p.status.name,
            categories = p.categoryIds.toSet(),
            quantity = p.inventory.quantity,
            createdAt = p.createdAt.toString(),
            updatedAt = p.updatedAt.toString()
        )
    }
}