package com.bbyoda.insighthub.order.interfaces.rest

import com.bbyoda.insighthub.order.application.dto.OrderError
import com.bbyoda.insighthub.order.application.usecase.*
import com.bbyoda.insighthub.shared.kernel.Result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val placeOrder: PlaceOrderUseCase,
    private val confirmPayment: ConfirmPaymentUseCase,
    private val shipOrder: ShipOrderUseCase,
    private val completeOrder: CompleteOrderUseCase,
    private val cancelOrder: CancelOrderUseCase,
    private val getOrder: GetOrderUseCase,
    private val listOrders: ListOrdersUseCase
) {
    @PostMapping
    fun create(@RequestBody body: CreateOrderRequest): ResponseEntity<*> =
        when (val res = placeOrder.execute(
            PlaceOrderUseCase.Cmd(
                userId = body.userId,
                items = body.items.map {
                    PlaceOrderUseCase.Cmd.Item(
                        productId = it.productId, name = it.name,
                        priceAmount = it.priceAmount, currency = it.currency, quantity = it.quantity
                    )
                }
            )
        )) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @PostMapping("/{orderId}/pay")
    fun pay(@PathVariable orderId: String, @RequestBody body: PayRequest): ResponseEntity<*> =
        when (val res = confirmPayment.execute(ConfirmPaymentUseCase.Cmd(orderId, body.customerId))) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @PostMapping("/{orderId}/ship")
    fun ship(@PathVariable orderId: String, @RequestBody body: ShipRequest): ResponseEntity<*> =
        when (val res = shipOrder.execute(ShipOrderUseCase.Cmd(orderId, body.trackingNumber))) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @PostMapping("/{orderId}/complete")
    fun complete(@PathVariable orderId: String): ResponseEntity<*> =
        when (val res = completeOrder.execute(orderId)) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @PostMapping("/{orderId}/cancel")
    fun cancel(@PathVariable orderId: String, @RequestBody body: CancelRequest): ResponseEntity<*> =
        when (val res = cancelOrder.execute(CancelOrderUseCase.Cmd(orderId, body.reason))) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @GetMapping("/{orderId}")
    fun get(@PathVariable orderId: String): ResponseEntity<*> =
        when (val res = getOrder.byId(orderId)) {
            is Result.Success -> ResponseEntity.ok(res.value)
            is Result.Failure -> toProblem(res.error)
        }

    @GetMapping
    fun listByUser(@RequestParam userId: String, @RequestParam(defaultValue = "50") limit: Int): ResponseEntity<*> =
        ResponseEntity.ok(listOrders.byUser(userId, limit))

    data class CreateOrderRequest(val userId: String, val items: List<Item>) {
        data class Item(
            val productId: String,
            val name: String,
            val priceAmount: Double,
            val currency: String,
            val quantity: Int
        )
    }

    data class PayRequest(val customerId: String)
    data class ShipRequest(val trackingNumber: String)
    data class CancelRequest(val reason: String)

    private fun toProblem(err: OrderError): ResponseEntity<*> =
        ResponseEntity.status(
            when (err) {
                is OrderError.NotFound -> 404
                is OrderError.EmptyOrder -> 400
                is OrderError.PaymentFailed -> 402
                is OrderError.InventoryNotReserved -> 409
                is OrderError.InvalidState -> 409
            }
        ).body(mapOf("error" to err.code, "message" to err.message))
}
