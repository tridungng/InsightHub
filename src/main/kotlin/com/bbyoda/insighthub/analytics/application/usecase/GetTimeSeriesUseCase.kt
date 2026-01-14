package com.bbyoda.insighthub.analytics.application.usecase

import java.time.LocalDate

import com.bbyoda.insighthub.analytics.domain.repository.AnalyticsRepository

class GetTimeSeriesUseCase(private val repo: AnalyticsRepository) {

    fun latest(days: Int = 14): List<AnalyticsRepository.DailySnapshot> =
        repo.getLatest(days)

    fun range(from: LocalDate, to: LocalDate): List<AnalyticsRepository.DailySnapshot> =
        repo.getRange(from, to)
}