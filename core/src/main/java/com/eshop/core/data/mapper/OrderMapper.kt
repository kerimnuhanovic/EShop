package com.eshop.core.data.mapper

import com.eshop.core.data.remote.dto.OrderDetailsDto
import com.eshop.core.data.remote.dto.OrderDto
import com.eshop.core.domain.models.Order
import com.eshop.core.domain.models.OrderDetails
import com.eshop.core.util.OrderStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun OrderDetailsDto.toOrderDetails(): OrderDetails {
    return OrderDetails(
        id = this.id,
        shop = this.shop,
        items = this.items,
        status = OrderStatus.toOrderStatus(this.status)
    )
}

fun OrderDetails.toOrderDetailsDto(): OrderDetailsDto {
    return OrderDetailsDto(
        id = this.id,
        shop = this.shop,
        items = this.items,
        status = this.status.name
    )
}

fun OrderDto.toOrder(): Order {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateOrderIsCreated = LocalDate.parse(this.dateCreated, inputFormatter)
    return Order(
        id = this.id,
        customer = this.customer,
        orderDetails = this.orderDetails.map {
            it.toOrderDetails()
        },
        dateCreated = dateOrderIsCreated
    )
}