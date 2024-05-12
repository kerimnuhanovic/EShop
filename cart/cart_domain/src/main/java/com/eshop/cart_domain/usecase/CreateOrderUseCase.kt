package com.eshop.cart_domain.usecase

import com.eshop.cart_domain.repository.CartRepository
import com.eshop.core.domain.models.Order
import com.eshop.core.domain.models.OrderDetails
import com.eshop.core.util.Result
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(orderDetails: List<OrderDetails>): Result<Order> {
        return cartRepository.createOrder(orderDetails)
    }
}