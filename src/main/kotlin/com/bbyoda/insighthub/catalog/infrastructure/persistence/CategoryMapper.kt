package com.bbyoda.insighthub.catalog.infrastructure.persistence

import com.bbyoda.insighthub.catalog.domain.model.Category
import org.springframework.stereotype.Component

@Component
class CategoryMapper {

    fun toDomain(entity: CategoryEntity): Category =
        Category(
            id = entity.id,
            name = entity.name,
            parentId = entity.parentId
        )

    fun toEntity(domain: Category): CategoryEntity =
        CategoryEntity(
            id = domain.id,
            name = domain.name,
            parentId = domain.parentId
        )
}