package com.bbyoda.insighthub.catalog.infrastructure.persistence

import com.bbyoda.insighthub.catalog.domain.model.Product
import com.bbyoda.insighthub.catalog.domain.repository.ProductRepository
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val jpa: ProductJpaRepository,
    private val mapper: ProductMapper
) : ProductRepository {

    override fun save(product: Product): Product =
        mapper.toDomain(jpa.save(mapper.toEntity(product)))

    override fun findById(id: String): Product? =
        jpa.findById(id).orElse(null)?.let(mapper::toDomain)

    override fun findByIds(ids: Collection<String>): List<Product> =
        jpa.findAllById(ids).map(mapper::toDomain)
}
