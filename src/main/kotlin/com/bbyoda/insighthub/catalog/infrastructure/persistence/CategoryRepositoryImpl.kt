package com.bbyoda.insighthub.catalog.infrastructure.persistence

import com.bbyoda.insighthub.catalog.domain.model.Category
import com.bbyoda.insighthub.catalog.domain.repository.CategoryRepository
import org.springframework.stereotype.Repository

@Repository
class CategoryRepositoryImpl(
    private val jpa: CategoryJpaRepository,
    private val mapper: CategoryMapper
) : CategoryRepository {

    override fun save(category: Category): Category =
        mapper.toDomain(jpa.save(CategoryEntity(category.id, category.name, category.parentId)))


    override fun findById(id: String): Category? =
        jpa.findById(id).orElse(null)?.let(mapper::toDomain)

    override fun findAll(): List<Category> =
        jpa.findAll().map(mapper::toDomain)
}
