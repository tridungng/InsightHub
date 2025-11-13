package com.bbyoda.insighthub.catalog.application

import com.bbyoda.insighthub.catalog.domain.ProductRepository
import com.bbyoda.insighthub.catalog.domain.events.StockUpdatedEvent
import com.bbyoda.insighthub.catalog.infrastructure.ProductEventPublisher
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class InventoryService(
    private val productRepository: ProductRepository,
    private val productEventPublisher: ProductEventPublisher
) {

    @Transactional
    fun updateStock(productId: UUID, newStock: Int) {
        val product =
            productRepository.findById(productId) ?: throw IllegalArgumentException("Product $productId not found")
        product.stock = newStock
        productRepository.save(product)

        productEventPublisher.publishStockUpdated(
            StockUpdatedEvent(productId, newStock)
        )
    }
}