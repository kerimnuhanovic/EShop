package com.eshop.productoverview_domain.usecase

import com.eshop.core.util.Result
import com.eshop.core.domain.models.Product
import com.eshop.productoverview_domain.repository.ProductOverviewRepository
import javax.inject.Inject

class FetchAllProductsUseCase @Inject constructor(
    private val productOverviewRepository: ProductOverviewRepository
) {
    suspend operator fun invoke(offset: Int): Result<List<Product>> {
        return productOverviewRepository.fetchAllProducts(offset)
    }
}