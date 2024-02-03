package com.eshop.product_domain.usecase

import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result
import com.eshop.product_domain.repository.ProductRepository
import javax.inject.Inject

class FetchProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: String): Result<Product> {
        return productRepository.fetchProduct(productId)
    }
}