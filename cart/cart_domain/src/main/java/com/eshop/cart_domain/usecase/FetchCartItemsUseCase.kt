package com.eshop.cart_domain.usecase

import com.eshop.cart_domain.repository.CartRepository
import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result
import javax.inject.Inject

class FetchCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return cartRepository.fetchCartItems()
    }
}