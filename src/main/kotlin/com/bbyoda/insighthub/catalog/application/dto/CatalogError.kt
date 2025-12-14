package com.bbyoda.insighthub.catalog.application.dto

sealed class CatalogError(val code: String, val message: String) {
    object ProductNotFound : CatalogError("PRODUCT_NOT_FOUND", "Product not found")
    object CategoryNotFound : CatalogError("CATEGORY_NOT_FOUND", "Category not found")
    object InvalidPrice : CatalogError("INVALID_PRICE", "Invalid price")
}