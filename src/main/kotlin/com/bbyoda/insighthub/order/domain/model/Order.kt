package com.bbyoda.insighthub.order.domain.model

import com.bbyoda.insighthub.shared.kernel.AggregateRoot
import com.bbyoda.insighthub.shared.types.Money
import com.bbyoda.insighthub.shared.types.UserId
import com.bbyoda.insighthub.order.domain.event.*

import java.time.Instant
import java.util.*

class Order(
    val id: String = UUID.randomUUID().toString(),
    val userId: UserId,
    private val items: MutableList<OrderItem> = mutableListOf(),
    var status: OrderStatus = OrderStatus.PENDING,
    var paymentStatus: PaymentStatus = PaymentStatus.UNPAID,
    val createdAt: Instant = Instant.now()
) : AggregateRoot<String>() {

    fun itemsView(): List<OrderItem> = items.toList()

    fun addItem(item: OrderItem) {
        require(status == OrderStatus.PENDING || status == OrderStatus.RESERVED) { "Cannot modify items after payment." }
        items.add(item)
    }

    fun total(): Money {
        val zero = Money.of(0.0, items.firstOrNull()?.unitPrice?.currency ?: "USD")
        return items.fold(zero) { acc, it -> acc + it.lineTotal() }
    }

    fun place() {
        require(items.isNotEmpty()) { "Order must contain at least one item" }
        require(status == OrderStatus.PENDING || status == OrderStatus.RESERVED) { "Invalid state to place order" }
        addDomainEvent(OrderPlaced(id, userId.value, total()))
    }

    fun markReserved() {
        require(status == OrderStatus.PENDING || status == OrderStatus.RESERVED)
        status = OrderStatus.RESERVED
        addDomainEvent(StockReserved(id))
    }

    fun markReservationFailed(reason: String) {
        // You could cancel automatically or keep pending; here we keep pending
        addDomainEvent(StockReservationFailed(id, reason))
    }

    fun markPaid(transactionId: String) {
        require(status == OrderStatus.PENDING || status == OrderStatus.RESERVED) { "Order already processed" }
        paymentStatus = PaymentStatus.CAPTURED
        status = OrderStatus.PAID
        addDomainEvent(OrderPaid(id, transactionId))
    }

    fun startFulfillment() {
        require(status == OrderStatus.PAID) { "Only paid orders can start fulfillment" }
        status = OrderStatus.FULFILLING
        addDomainEvent(FulfillmentStarted(id))
    }

    fun markShipped(trackingNumber: String) {
        require(status == OrderStatus.FULFILLING || status == OrderStatus.PAID) { "Order must be paid/fulfilling to ship" }
        status = OrderStatus.SHIPPED
        addDomainEvent(OrderShipped(id, trackingNumber))
    }

    fun complete() {
        require(status == OrderStatus.SHIPPED) { "Order must be shipped to complete" }
        status = OrderStatus.COMPLETED
        addDomainEvent(OrderCompleted(id))
    }

    fun cancel(reason: String) {
        require(status != OrderStatus.COMPLETED && status != OrderStatus.CANCELLED) { "Cannot cancel completed/cancelled order" }
        status = OrderStatus.CANCELLED
        addDomainEvent(OrderCancelled(id, reason))
    }
}
