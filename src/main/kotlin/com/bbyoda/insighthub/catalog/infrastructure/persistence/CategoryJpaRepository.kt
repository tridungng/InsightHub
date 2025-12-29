package com.bbyoda.insighthub.catalog.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<CategoryEntity, String>
