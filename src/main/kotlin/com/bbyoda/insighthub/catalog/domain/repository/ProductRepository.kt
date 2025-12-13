package com.bbyoda.insighthub.catalog.domain.repository

import com.bbyoda.insighthub.catalog.domain.Product

interface ProductRepository {
    fun save(product: Product): Product
    fun findById(id: String): Product?
    fun findByIds(ids: Collection<String>): List<Product>
}