package com.eshop.productoverview_domain.usecase

import com.eshop.core.util.Result
import com.eshop.productoverview_domain.model.Product
import com.eshop.productoverview_domain.repository.ProductRepository
import javax.inject.Inject

class FetchAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(offset: Int): Result<List<Product>> {
        return productRepository.fetchAllProducts(offset)
    }
}