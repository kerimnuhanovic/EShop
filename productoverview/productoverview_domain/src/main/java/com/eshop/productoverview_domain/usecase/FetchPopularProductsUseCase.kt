package com.eshop.productoverview_domain.usecase

import com.eshop.productoverview_domain.repository.ProductRepository
import javax.inject.Inject
import com.eshop.core.util.Result
import com.eshop.productoverview_domain.model.Product

class FetchPopularProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return productRepository.fetchPopularProducts()
    }
}