package com.bbyoda.insighthub.catalog.interfaces

import com.bbyoda.insighthub.catalog.application.CatalogService
import com.bbyoda.insighthub.catalog.application.InventoryService
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
    fun createProduct(name: String, description: String, price: BigDecimal, stock: Int) =
        catalogService.createProduct(name, description, price, stock)

    @MutationMapping
    fun updateStock(id: UUID, newStock: Int): Boolean {
        inventoryService.updateStock(id, newStock)
        return true
    }
}