package com.bbyoda.insighthub.shared.types

import java.math.BigDecimal
import java.math.RoundingMode

data class Money(
    val amount: BigDecimal,
    val currency: String
) {
    init {
        require(amount.scale() <= 2) { "Money amount cannot have more than 2 decimal places" }
        require(currency.matches(Regex("^[A-Z]{3}$"))) { "Invalid currency code: $currency" }
    }

    operator fun plus(other: Money): Money {
        require(currency == other.currency) { "Cannot add different currencies" }
        return copy(amount = amount.add(other.amount))
    }

    operator fun minus(other: Money): Money {
        require(currency == other.currency) { "Cannot subtract different currencies" }
        return copy(amount = amount.subtract(other.amount))
    }

    fun multiply(multiplier: BigDecimal): Money =
        copy(amount = amount.multiply(multiplier).setScale(2, RoundingMode.HALF_UP))

    fun isPositive(): Boolean = amount > BigDecimal.ZERO

    fun isZero(): Boolean = amount.compareTo(BigDecimal.ZERO) == 0

    override fun toString(): String = "$amount $currency"

    companion object {
        fun of(value: Double, currency: String): Money =
            Money(BigDecimal(value).setScale(2, RoundingMode.HALF_UP), currency)
    }
}
