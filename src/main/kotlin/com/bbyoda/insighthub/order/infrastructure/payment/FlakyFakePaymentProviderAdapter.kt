package com.bbyoda.insighthub.order.infrastructure.payment

import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

import com.bbyoda.insighthub.order.application.port.PaymentPort
import com.bbyoda.insighthub.shared.types.Money

@Component
@Profile("dev")
class FlakyFakePaymentProviderAdapter : PaymentPort {

    companion object {
        private val log = LoggerFactory.getLogger(FlakyFakePaymentProviderAdapter::class.java)
    }

    private val random = java.util.Random()

    override fun charge(orderId: String, amount: Money, customerId: String): PaymentPort.ChargeResult {
        if (!amount.isPositive()) {
            return PaymentPort.ChargeResult(false, failureReason = "Invalid amount")
        }

        // 80% success, 20% failure
        val success = random.nextDouble() < 0.8

        return if (success) {
            val txId = "FAKE-${UUID.randomUUID()}"
            log.info("FlakyFakePaymentProvider: SUCCESS order={} amount={} txId={}", orderId, amount, txId)
            PaymentPort.ChargeResult(true, transactionId = txId)
        } else {
            log.warn("FlakyFakePaymentProvider: FAILURE order={} amount={}", orderId, amount)
            PaymentPort.ChargeResult(false, failureReason = "Random simulated failure")
        }
    }
}
