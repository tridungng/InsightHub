package com.bbyoda.insighthub.notification.domain.model

enum class NotificationChannel {
    EMAIL,
    SMS,
    PUSH,
    WEBHOOK,
    WEBSOCKET   // for live-streaming to UI
}