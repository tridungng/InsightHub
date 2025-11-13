package com.bbyoda.insighthub.catalog.infrastructure

import com.bbyoda.insighthub.catalog.domain.events.ProductCreatedEvent
import com.bbyoda.insighthub.catalog.domain.events.StockUpdatedEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ProductEventPublisher(private val kafkaTemplate: KafkaTemplate<String, Any>) {

    fun publishProductCreated(event: ProductCreatedEvent) {
        kafkaTemplate.send("product-created", event.id.toString(), event)
    }

    fun publishStockUpdated(event: StockUpdatedEvent) {
        kafkaTemplate.send("stock-updated", event.id.toString(), event)
    }
}