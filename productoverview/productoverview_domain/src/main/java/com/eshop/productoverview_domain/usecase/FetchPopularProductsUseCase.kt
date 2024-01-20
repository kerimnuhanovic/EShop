package com.eshop.productoverview_domain.usecase

import com.eshop.productoverview_domain.repository.ProductOverviewRepository
import javax.inject.Inject
import com.eshop.core.util.Result
import com.eshop.core.domain.models.Product

class FetchPopularProductsUseCase @Inject constructor(
    private val productOverviewRepository: ProductOverviewRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return productOverviewRepository.fetchPopularProducts()
    }
}