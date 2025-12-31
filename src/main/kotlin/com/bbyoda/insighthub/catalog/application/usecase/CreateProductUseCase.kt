package com.bbyoda.insighthub.catalog.application.usecase

import java.math.BigDecimal
import org.springframework.stereotype.Service

import com.bbyoda.insighthub.catalog.application.dto.CatalogError
import com.bbyoda.insighthub.catalog.application.dto.ProductDto
import com.bbyoda.insighthub.catalog.application.port.CatalogSearchPort
import com.bbyoda.insighthub.catalog.application.port.EventPublisher
import com.bbyoda.insighthub.catalog.domain.model.Price
import com.bbyoda.insighthub.catalog.domain.model.Product
import com.bbyoda.insighthub.catalog.domain.repository.CategoryRepository
import com.bbyoda.insighthub.catalog.domain.repository.ProductRepository
import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.shared.types.Money

@Service
class CreateProductUseCase(
    private val products: ProductRepository,
    private val categories: CategoryRepository,
    private val events: EventPublisher,
    private val search: CatalogSearchPort
) {
    data class Cmd(
        val name: String,
        val description: String?,
        val priceAmount: Double,
        val currency: String,
        val categoryIds: Set<String>
    )

    fun execute(cmd: Cmd): Result<ProductDto, CatalogError> {
        if (cmd.priceAmount < 0) return Result.failure(CatalogError.InvalidPrice)

        cmd.categoryIds.forEach { id ->
            if (categories.findById(id) == null) {
                return Result.failure(CatalogError.CategoryNotFound)
            }
        }

        val money = Money(BigDecimal(cmd.priceAmount).setScale(2), cmd.currency)
        val product = Product.create(
            name = cmd.name,
            description = cmd.description,
            price = Price(money),
            categoryIds = cmd.categoryIds
        )

        val saved = products.save(product)
        events.publish(saved.domainEvents)
        saved.clearDomainEvents()

        val dto = ProductDto.fromDomain(saved)
        search.index(dto)

        return Result.success(dto)
    }
}
