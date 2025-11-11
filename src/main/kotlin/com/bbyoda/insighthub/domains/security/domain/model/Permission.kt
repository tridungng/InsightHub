package com.bbyoda.insighthub.domains.security.domain.model

enum class Permission {
    // User management
    USER_READ, USER_WRITE, USER_DELETE,

    // Product/Catalog
    PRODUCT_READ, PRODUCT_WRITE, PRODUCT_DELETE,
    CATEGORY_READ, CATEGORY_WRITE, CATEGORY_DELETE,

    // Order
    ORDER_READ, ORDER_WRITE, ORDER_DELETE, ORDER_APPROVE,

    // System
    SYSTEM_ADMIN
}