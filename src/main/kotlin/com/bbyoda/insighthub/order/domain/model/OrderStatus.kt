package com.bbyoda.insighthub.order.domain.model

enum class OrderStatus {
    PENDING,          // created, not paid
    RESERVED,         // stock reserved (optional step if you use inventory reservation)
    PAID,             // payment confirmed
    FULFILLING,       // being shipped/prepared
    SHIPPED,          // shipped
    COMPLETED,        // delivered / closed
    CANCELLED
}