package com.bbyoda.insighthub.analytics.application.dto

import com.bbyoda.insighthub.analytics.domain.repository.AnalyticsRepository
import java.time.LocalDate

data class DashboardDto(
    val from: LocalDate,
    val to: LocalDate,
    val totals: TotalsDto,
    val daily: List<DailyDto>
) {
    data class TotalsDto(
        val usersCreated: Long,
        val ordersPlaced: Long,
        val ordersPaid: Long,
        val ordersCompleted: Long,
        val revenueMinor: Long,
        val revenueCurrency: String,
        val notificationsSent: Long,
        val notificationsFailed: Long
    )

    data class DailyDto(
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

    companion object {
        fun fromSnapshots(
            from: LocalDate,
            to: LocalDate,
            snapshots: List<AnalyticsRepository.DailySnapshot>
        ): DashboardDto {
            val totals = snapshots.fold(
                TotalsDto(0, 0, 0, 0, 0, snapshots.firstOrNull()?.revenueCurrency ?: "USD", 0, 0)
            ) { acc, s ->
                acc.copy(
                    usersCreated = acc.usersCreated + s.usersCreated,
                    ordersPlaced = acc.ordersPlaced + s.ordersPlaced,
                    ordersPaid = acc.ordersPaid + s.ordersPaid,
                    ordersCompleted = acc.ordersCompleted + s.ordersCompleted,
                    revenueMinor = acc.revenueMinor + s.revenueMinor,
                    revenueCurrency = s.revenueCurrency.ifBlank { acc.revenueCurrency },
                    notificationsSent = acc.notificationsSent + s.notificationsSent,
                    notificationsFailed = acc.notificationsFailed + s.notificationsFailed
                )
            }

            return DashboardDto(
                from = from,
                to = to,
                totals = totals,
                daily = snapshots.map {
                    DailyDto(
                        day = it.day,
                        usersCreated = it.usersCreated,
                        ordersPlaced = it.ordersPlaced,
                        ordersPaid = it.ordersPaid,
                        ordersCompleted = it.ordersCompleted,
                        revenueMinor = it.revenueMinor,
                        revenueCurrency = it.revenueCurrency,
                        notificationsSent = it.notificationsSent,
                        notificationsFailed = it.notificationsFailed
                    )
                }
            )
        }
    }
}
