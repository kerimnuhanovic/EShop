package com.eshop.product_domain.usecase

import com.eshop.core.util.Result
import com.eshop.product_domain.model.CartItem
import com.eshop.product_domain.repository.ProductRepository
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: String): Result<CartItem> {
        return productRepository.addProductToCart(productId)
    }
}