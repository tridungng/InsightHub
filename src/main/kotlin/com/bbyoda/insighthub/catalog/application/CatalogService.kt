package com.bbyoda.insighthub.catalog.application

import com.bbyoda.insighthub.catalog.domain.Category
import com.bbyoda.insighthub.catalog.domain.Product
import com.bbyoda.insighthub.catalog.domain.ProductRepository
import com.bbyoda.insighthub.catalog.domain.events.ProductCreatedEvent
import com.bbyoda.insighthub.catalog.infrastructure.ProductEventPublisher
import jakarta.transaction.Transactional

import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CatalogService(
    private val productRepository: ProductRepository,
    private val productEventPublisher: ProductEventPublisher
) {

    @Transactional
    fun createProduct(name: String, description: String, price: BigDecimal, stock: Int): Product {
        val product = Product(name = name, description = description, price = price, stock = stock, category = null)
        val savedProduct = productRepository.save(product)

        productEventPublisher.publishProductCreated(
            ProductCreatedEvent(
                savedProduct.id,
                savedProduct.name,
                savedProduct.price,
                savedProduct.stock
            )
        )

        return savedProduct
    }

    fun getAllProducts(): List<Product> = productRepository.findAll()
}