package com.eshop.core.data.mapper

import com.eshop.core.data.remote.dto.OrderDetailsWithProductsDto
import com.eshop.core.data.remote.dto.OrderWithProductsDto
import com.eshop.core.domain.models.OrderDetailsWithProducts
import com.eshop.core.domain.models.OrderWithProducts
import com.eshop.core.util.OrderStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun OrderDetailsWithProductsDto.toOrderDetailsWithProducts(): OrderDetailsWithProducts {
    return OrderDetailsWithProducts(
        id = id,
        shop = shop,
        items = items.map { it.toProduct() },
        status = OrderStatus.toOrderStatus(orderStatus = status)
    )
}

fun OrderWithProductsDto.toOrderWithProducts(): OrderWithProducts {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateOrderIsCreated = LocalDate.parse(this.dateCreated, inputFormatter)
    return OrderWithProducts(
        id = id,
        customer = customer,
        orderDetails = orderDetails.map { it.toOrderDetailsWithProducts() },
        dateCreated = dateOrderIsCreated
    )
}