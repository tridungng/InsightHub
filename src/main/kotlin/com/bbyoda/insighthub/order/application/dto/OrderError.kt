package com.bbyoda.insighthub.order.application.dto

sealed class OrderError(val code: String, val message: String) {
    object NotFound : OrderError("ORDER_NOT_FOUND", "Order not found")
    object EmptyOrder : OrderError("EMPTY_ORDER", "Order has no items")
    data class PaymentFailed(val reason: String) : OrderError("PAYMENT_FAILED", reason)
    data class InventoryNotReserved(val reason: String) : OrderError("RESERVATION_FAILED", reason)
    object InvalidState : OrderError("INVALID_STATE", "Operation not allowed in current state")
}