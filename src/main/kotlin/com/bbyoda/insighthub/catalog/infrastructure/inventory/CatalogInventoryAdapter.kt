package com.bbyoda.insighthub.catalog.infrastructure.inventory

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

import com.bbyoda.insighthub.order.application.port.InventoryPort
import com.bbyoda.insighthub.catalog.domain.repository.ProductRepository

@Component
class CatalogInventoryAdapter(
    private val productRepository: ProductRepository
) : InventoryPort {

    @Transactional
    override fun reserve(orderId: String, lines: List<InventoryPort.Line>): Boolean {
        if (lines.isEmpty()) return true

        // Load all products upfront
        val ids = lines.map { it.productId }.toSet()
        val products = productRepository.findByIds(ids).associateBy { it.id }

        // 1. Validate availability
        for (line in lines) {
            val product = products[line.productId] ?: run {
                return false
            }

            val available = product.inventory.quantity // or available()
            if (available < line.quantity) {
                return false
            }
        }

        // 2. Apply stock changes (simple hard-reserve: decrease quantity)
        lines.forEach { line ->
            val product = products[line.productId]!!
            product.updateStock(-line.quantity)
            productRepository.save(product)
        }

        return true
    }

    @Transactional
    override fun release(orderId: String) {
        // In a more robust design, you'd have a stock_reservations table:
        //  - reservation(orderId, productId, quantity)
        // and look it up here to add stock back.
        //
        // For now this can be a no-op or just log. Real enterprise flow:
        // - load reservations by orderId
        // - increment product.stock by reserved amount
        // - delete reservation rows

    }
}
