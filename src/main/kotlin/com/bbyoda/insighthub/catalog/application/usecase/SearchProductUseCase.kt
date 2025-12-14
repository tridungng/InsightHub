package com.bbyoda.insighthub.catalog.application.usecase

import com.bbyoda.insighthub.catalog.application.dto.ProductDto
import com.bbyoda.insighthub.catalog.application.port.CatalogSearchPort

class SearchProductsUseCase(
    private val search: CatalogSearchPort
) {
    fun execute(query: String, limit: Int = 20): List<ProductDto> =
        search.search(query, limit)
}