package com.bbyoda.insighthub.catalog.domain.model

import com.bbyoda.insighthub.shared.types.Money

data class Price(val value: Money, val isDiscounted: Boolean = false) {
    override fun toString(): String = value.toString()
}