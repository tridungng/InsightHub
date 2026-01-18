package com.bbyoda.insighthub.analytics.infrastructure.persistence

import java.time.LocalDate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

import com.bbyoda.insighthub.analytics.domain.model.MetricName
import com.bbyoda.insighthub.analytics.domain.repository.AnalyticsRepository

@Repository
class AnalyticsRepositoryImpl(
    private val dailyRepo: DailyMetricsJpaRepository
) : AnalyticsRepository {

    @Transactional
    override fun increment(
        day: LocalDate,
        metric: MetricName,
        delta: Long
    ) {
        val row = dailyRepo.findById(day).orElse(DailyMetricsEntity(day))

        when (metric) {
            MetricName.USERS_CREATED -> row.usersCreated += delta
            MetricName.ORDERS_PLACED -> row.ordersPlaced += delta
            MetricName.ORDERS_PAID -> row.ordersPaid += delta
            MetricName.ORDERS_COMPLETED -> row.ordersCompleted += delta
            MetricName.NOTIFICATIONS_SENT -> row.notificationsSent += delta
            MetricName.NOTIFICATIONS_FAILED -> row.notificationsFailed += delta
            MetricName.REVENUE -> row.revenueMinor += delta
        }
        dailyRepo.save(row)
    }

    override fun addRevenue(day: LocalDate, currency: String, amountMinor: Long) {
        TODO("Not yet implemented")
    }

    override fun getRange(
        from: LocalDate,
        to: LocalDate
    ): List<AnalyticsRepository.DailySnapshot> {
        TODO("Not yet implemented")
    }

    override fun getLatest(days: Int): List<AnalyticsRepository.DailySnapshot> {
        TODO("Not yet implemented")
    }
}