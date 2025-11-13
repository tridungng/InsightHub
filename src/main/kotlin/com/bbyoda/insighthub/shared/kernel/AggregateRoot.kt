package com.bbyoda.insighthub.shared.kernel

abstract class AggregateRoot<ID> {

    private val _domainEvents = mutableListOf<DomainEvent>()

    val domainEvents: List<DomainEvent>
        get() = _domainEvents.toList()

    protected fun addDomainEvent(event: DomainEvent) {
        _domainEvents.add(event)
    }

    fun clearDomainEvents() = _domainEvents.clear()
}