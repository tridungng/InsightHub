package com.bbyoda.insighthub.catalog.domain.repository

import com.bbyoda.insighthub.catalog.domain.model.Category

interface CategoryRepository {
    fun save(category: Category): Category
    fun findById(id: String): Category?
    fun findAll(): List<Category>
}