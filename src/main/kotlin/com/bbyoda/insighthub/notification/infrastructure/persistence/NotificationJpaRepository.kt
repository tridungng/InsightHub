package com.bbyoda.insighthub.notification.infrastructure.persistence

import com.bbyoda.insighthub.notification.domain.model.NotificationStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationJpaRepository : JpaRepository<NotificationEntity, String> {

    fun findByRecipientUserIdOrderByCreatedAtDesc(userId: String): List<NotificationEntity>

    fun findByStatusOrderByCreatedAtAsc(status: NotificationStatus): List<NotificationEntity>

    @Query(
        "select n from NotificationEntity n where n.status = :status order by n.createdAt asc limit :limit",
        nativeQuery = false
    )
    fun findTopByStatus(status: NotificationStatus, limit: Int): List<NotificationEntity>
}
