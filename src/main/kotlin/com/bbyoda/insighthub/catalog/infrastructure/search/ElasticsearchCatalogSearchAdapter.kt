package com.bbyoda.insighthub.catalog.infrastructure.search

import org.springframework.stereotype.Component

import com.bbyoda.insighthub.catalog.application.dto.ProductDto
import com.bbyoda.insighthub.catalog.application.port.CatalogSearchPort

@Component
class ElasticsearchCatalogSearchAdapter(
    private val repo: ProductSearchRepository
) : CatalogSearchPort {

    override fun index(product: ProductDto) {
        val doc = ProductSearchDocument(
            id = product.id,
            name = product.name,
            description = product.description,
            categories = product.categories
        )

        repo.save(doc)
    }

    override fun remove(productId: String) {
        repo.deleteById(productId)
    }

    override fun search(query: String, limit: Int): List<ProductDto> {
        val docs = repo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query)
        return docs.take(limit).map {
            ProductDto(
                id = it.id,
                name = it.name,
                description = it.description,
                price = "",
                status = "",
                categories = it.categories,
                quantity = 0,
                createdAt = "",
                updatedAt = ""
            )
        }
    }
}