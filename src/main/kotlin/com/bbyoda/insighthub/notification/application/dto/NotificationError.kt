package com.bbyoda.insighthub.notification.application.dto

sealed class NotificationError(val code: String, val message: String) {
    object NotFound : NotificationError("NOTIFICATION_NOT_FOUND", "Notification not found")
}