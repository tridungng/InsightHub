package com.bbyoda.insighthub.shared.kernel

abstract class AggregateRoot<ID> {
    abstract val id: ID

    private val domainEvents: MutableList<DomainEvent> = mutableListOf()

    protected fun addDomainEvent(event: DomainEvent) {
        domainEvents.add(event)
    }

    fun getDomainEvents(): List<DomainEvent> = domainEvents.toList()

    fun clearDomainEvents() = domainEvents.clear()
}