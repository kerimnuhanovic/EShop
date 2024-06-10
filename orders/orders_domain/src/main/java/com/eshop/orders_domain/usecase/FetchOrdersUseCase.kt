package com.eshop.orders_domain.usecase

import com.eshop.core.domain.models.OrderWithProducts
import com.eshop.core.util.Result
import com.eshop.core.util.UserType
import com.eshop.orders_domain.repository.OrdersRepository
import javax.inject.Inject

class FetchOrdersUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(userType: UserType): Result<List<OrderWithProducts>> {
        return ordersRepository.fetchOrders(userType)
    }
}