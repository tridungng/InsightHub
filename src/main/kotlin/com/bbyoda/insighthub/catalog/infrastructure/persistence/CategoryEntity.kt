package com.bbyoda.insighthub.catalog.infrastructure.persistence

import jakarta.persistence.*

@Entity
@Table(name = "categories")
class CategoryEntity(
    @Id
    val id: String,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(name = "parent_id")
    val parentId: String? = null
) {
    constructor() : this(
        id = "",
        name = "",
        parentId = null
    )
}
