package com.bbyoda.insighthub.domains.catalog.interfaces

import com.bbyoda.insighthub.domains.catalog.application.CatalogService
import com.bbyoda.insighthub.domains.catalog.application.InventoryService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.math.BigDecimal
import java.util.UUID

@Controller
class CatalogGraphQL(private val catalogService: CatalogService, private val inventoryService: InventoryService) {

    @QueryMapping
    fun products() = catalogService.getAllProducts()

    @MutationMapping
    fun createProduct(@Argument input: CreateProductInput) =
        catalogService.createProduct(
            name = input.name,
            description = input.description,
            price = input.price,
            stock = input.stock
        )

    @MutationMapping
    fun updateStock(
        @Argument id: UUID,
        @Argument newStock: Int
    ): Boolean {
        inventoryService.updateStock(id, newStock)
        return true
    }
}

data class CreateProductInput(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int
)