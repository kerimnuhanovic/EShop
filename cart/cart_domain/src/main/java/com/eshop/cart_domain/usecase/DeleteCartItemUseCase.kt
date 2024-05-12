package com.eshop.cart_domain.usecase

import com.eshop.cart_domain.repository.CartRepository
import com.eshop.core.util.Result
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String): Result<Unit> {
        return cartRepository.deleteCartItem(productId)
    }
}