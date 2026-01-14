package com.bbyoda.insighthub.analytics.application.usecase

import java.time.LocalDate

import com.bbyoda.insighthub.shared.kernel.Result
import com.bbyoda.insighthub.analytics.application.dto.AnalyticsError
import com.bbyoda.insighthub.analytics.application.dto.DashboardDto
import com.bbyoda.insighthub.analytics.domain.repository.AnalyticsRepository

class GetDashboardUseCase(private val repo: AnalyticsRepository) {
    
    fun execute(from: LocalDate, to: LocalDate): Result<DashboardDto, AnalyticsError> {
        if (to.isBefore(from)) return Result.failure(AnalyticsError.InvalidRange)

        val snapshots = repo.getRange(from, to)
        return Result.success(DashboardDto.fromSnapshots(from, to, snapshots))
    }
}