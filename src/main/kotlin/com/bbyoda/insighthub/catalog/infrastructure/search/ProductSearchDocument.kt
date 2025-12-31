package com.bbyoda.insighthub.catalog.infrastructure.search

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "products")
data class ProductSearchDocument(
    @Id
    val id: String,
    val name: String,
    val description: String?,
    val categories: Set<String>
)
