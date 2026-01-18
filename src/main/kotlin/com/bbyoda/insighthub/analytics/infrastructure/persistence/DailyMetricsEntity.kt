package com.bbyoda.insighthub.analytics.infrastructure.persistence

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "analytics_daily_metrics")
class DailyMetricsEntity(
    @Id
    @Column(name = "day")
    val day: LocalDate,

    @Column(name = "users_created", nullable = false)
    var usersCreated: Long = 0,

    @Column(name = "orders_placed", nullable = false)
    var ordersPlaced: Long = 0,

    @Column(name = "orders_paid", nullable = false)
    var ordersPaid: Long = 0,

    @Column(name = "orders_completed", nullable = false)
    var ordersCompleted: Long = 0,

    @Column(name = "revenue_minor", nullable = false)
    var revenueMinor: Long = 0,

    @Column(name = "revenue_currency", nullable = false)
    var revenueCurrency: String = "USD",

    @Column(name = "notifications_sent", nullable = false)
    var notificationsSent: Long = 0,

    @Column(name = "notifications_failed", nullable = false)
    var notificationsFailed: Long = 0
) {
    constructor() : this(
        day = LocalDate.now(),
        usersCreated = 0,
        ordersPlaced = 0,
        ordersPaid = 0,
        ordersCompleted = 0,
        revenueMinor = 0,
        revenueCurrency = "USD",
        notificationsSent = 0,
        notificationsFailed = 0
    )

}
