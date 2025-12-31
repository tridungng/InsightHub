package com.bbyoda.insighthub.catalog.infrastructure.search

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ProductSearchRepository : ElasticsearchRepository<ProductSearchDocument, String> {

    fun findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        name: String,
        description: String,
    ): List<ProductSearchDocument>
}