package com.bbyoda.insighthub.notification.application.port

import com.bbyoda.insighthub.notification.domain.model.Notification
import com.bbyoda.insighthub.notification.domain.model.NotificationChannel

interface ChannelSender {
    fun supports(channel: NotificationChannel): Boolean
    fun send(notification: Notification): ChannelResult

    data class ChannelResult(
        val success: Boolean,
        val error: String? = null
    )
}