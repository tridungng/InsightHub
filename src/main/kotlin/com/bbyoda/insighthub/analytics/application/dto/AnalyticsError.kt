package com.bbyoda.insighthub.analytics.application.dto

sealed class AnalyticsError(val code: String, val message: String) {
    object InvalidRange : AnalyticsError("INVALID_RANGE", "Invalid date range")
}