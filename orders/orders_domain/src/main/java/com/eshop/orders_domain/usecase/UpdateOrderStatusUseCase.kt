package com.eshop.orders_domain.usecase

import com.eshop.core.util.OrderStatus
import com.eshop.core.util.Result
import com.eshop.orders_domain.repository.OrdersRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(id: String, orderDetailsId: String, status: OrderStatus): Result<Unit> {
        return ordersRepository.updateOrderStatus(id, orderDetailsId, status)
    }
}