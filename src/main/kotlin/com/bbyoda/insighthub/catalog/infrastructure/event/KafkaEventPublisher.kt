package com.bbyoda.insighthub.catalog.infrastructure.event

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

import com.bbyoda.insighthub.catalog.application.port.EventPublisher
import com.bbyoda.insighthub.shared.kernel.DomainEvent

@Component
class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : EventPublisher {

    override fun publish(events: Collection<DomainEvent>) {
        events.forEach { ev ->
            val topic = "catalog.${ev::class.simpleName}"
            kafkaTemplate.send(topic, ev)
        }
    }
}
