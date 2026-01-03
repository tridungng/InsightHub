package com.bbyoda.insighthub.notification.domain.repository

import com.bbyoda.insighthub.notification.domain.model.Notification

interface NotificationRepository {
    fun save(notification: Notification): Notification
    fun findById(id: String): Notification?
    fun findPending(limit: Int = 100): List<Notification>
    fun findByUserId(userId: String, limit: Int = 50): List<Notification>
}