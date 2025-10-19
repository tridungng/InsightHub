package com.bbyoda.insighthub.catalog.infrastructure

import com.bbyoda.insighthub.catalog.domain.Product
import com.bbyoda.insighthub.catalog.domain.ProductRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.UUID

interface SpringDataProductRepo : JpaRepository<Product, UUID>

@Repository
class JpaProductRepository(private val jpaRepo: SpringDataProductRepo) : ProductRepository {
    override fun save(product: Product) = jpaRepo.save(product)

    override fun findById(id: UUID) = jpaRepo.findByIdOrNull(id)

    override fun findAll() = jpaRepo.findAll()

}