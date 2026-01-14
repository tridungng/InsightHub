package com.bbyoda.insighthub.analytics.domain.repository

import com.bbyoda.insighthub.analytics.domain.model.MetricName
import java.time.LocalDate

interface AnalyticsRepository {
    fun increment(day: LocalDate, metric: MetricName, delta: Long = 1)
    fun addRevenue(day: LocalDate, currency: String, amountMinor: Long)
    fun getRange(from: LocalDate, to: LocalDate): List<DailySnapshot>
    fun getLatest(days: Int): List<DailySnapshot>

    data class DailySnapshot(
        val day: LocalDate,
        val usersCreated: Long,
        val ordersPlaced: Long,
        val ordersPaid: Long,
        val ordersCompleted: Long,
        val revenueMinor: Long,
        val revenueCurrency: String,
        val notificationsSent: Long,
        val notificationsFailed: Long
    )
}