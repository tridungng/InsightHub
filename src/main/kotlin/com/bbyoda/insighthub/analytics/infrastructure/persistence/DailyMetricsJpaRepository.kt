package com.bbyoda.insighthub.analytics.infrastructure.persistence

import java.time.LocalDate

import org.springframework.data.jpa.repository.JpaRepository

interface DailyMetricsJpaRepository : JpaRepository<DailyMetricsEntity, LocalDate>