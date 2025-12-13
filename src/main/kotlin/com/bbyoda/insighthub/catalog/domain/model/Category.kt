package com.bbyoda.insighthub.catalog.domain.model

data class Category(val id: String, val name: String, val parentId: String? = null)