package com.bbyoda.insighthub.catalog.application.port

import com.bbyoda.insighthub.catalog.application.dto.ProductDto

interface CatalogSearchPort {
    fun index(product: ProductDto)
    fun remove(productId: String)
    fun search(query: String, limit: Int = 20): List<ProductDto>
}