package com.bbyoda.insighthub.order.application.port

import com.bbyoda.insighthub.shared.types.Money

interface PaymentPort {
    fun charge(orderId: String, amount: Money, customerId: String): ChargeResult

    data class ChargeResult(
        val success: Boolean,
        val transactionId: String? = null,
        val failureReason: String? = null
    )
}
