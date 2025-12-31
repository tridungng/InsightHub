package com.bbyoda.insighthub.catalog.application.usecase

import org.springframework.stereotype.Service

import com.bbyoda.insighthub.catalog.application.dto.CatalogError
import com.bbyoda.insighthub.catalog.application.dto.ProductDto
import com.bbyoda.insighthub.catalog.application.port.EventPublisher
import com.bbyoda.insighthub.catalog.domain.repository.ProductRepository
import com.bbyoda.insighthub.shared.kernel.Result

@Service
class UpdateStockUseCase(
    private val products: ProductRepository,
    private val events: EventPublisher
) {
    data class Cmd(val productId: String, val delta: Int)

    fun execute(cmd: Cmd): Result<ProductDto, CatalogError> {
        val product = products.findById(cmd.productId)
            ?: return Result.failure(CatalogError.ProductNotFound)

        product.updateStock(cmd.delta)

        val saved = products.save(product)
        events.publish(saved.domainEvents)
        saved.clearDomainEvents()

        return Result.success(ProductDto.fromDomain(saved))
    }
}