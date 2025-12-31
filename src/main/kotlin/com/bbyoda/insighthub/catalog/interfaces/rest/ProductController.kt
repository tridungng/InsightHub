package com.bbyoda.insighthub.catalog.interfaces.rest

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

import com.bbyoda.insighthub.catalog.application.usecase.CreateProductUseCase
import com.bbyoda.insighthub.catalog.application.usecase.SearchProductsUseCase
import com.bbyoda.insighthub.catalog.application.usecase.UpdateStockUseCase
import com.bbyoda.insighthub.catalog.application.dto.CatalogError
import com.bbyoda.insighthub.shared.kernel.Result


@Controller("/api/v1/products")
class ProductController(
    private val createProduct: CreateProductUseCase,
    private val updateStock: UpdateStockUseCase,
    private val searchProducts: SearchProductsUseCase
) {

    @PostMapping
    fun create(@RequestBody body: CreateProductRequest): ResponseEntity<*> =
        when (val res = createProduct.execute(
            CreateProductUseCase.Cmd(
                name = body.name,
                description = body.description,
                priceAmount = body.priceAmount,
                currency = body.currency,
                categoryIds = body.categoryIds
            )
        )) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @PostMapping("/{id}/stock")
    fun updateStock(@PathVariable id: String, @RequestBody body: UpdateStockBody): ResponseEntity<*> =
        when (val res = updateStock.execute(UpdateStockUseCase.Cmd(id, body.delta))) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @GetMapping("/search")
    fun search(@RequestParam q: String, @RequestParam(defaultValue = "20") limit: Int): ResponseEntity<*> =
        ResponseEntity.ok(searchProducts.execute(q, limit))


    data class CreateProductRequest(
        val name: String,
        val description: String,
        val priceAmount: Double,
        val currency: String,
        val categoryIds: Set<String> = emptySet()
    )

    data class UpdateStockBody(val delta: Int)

    private fun toProblem(err: CatalogError): ResponseEntity<*> =
        ResponseEntity.status(
            when (err) {
                CatalogError.ProductNotFound -> 404
                CatalogError.CategoryNotFound -> 400
                CatalogError.InvalidPrice -> 400
            }
        ).body(mapOf("error" to err.code, "message" to err.message))
}