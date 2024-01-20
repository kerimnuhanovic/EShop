package com.eshop.productoverview_domain.usecase

import com.eshop.productoverview_domain.repository.ProductOverviewRepository
import javax.inject.Inject
import com.eshop.core.util.Result
import com.eshop.core.domain.models.Product
import com.eshop.productoverview_domain.model.ProductAdditionData

class AddProductUseCase @Inject constructor(
    private val productOverviewRepository: ProductOverviewRepository
) {
    suspend operator fun invoke(productData: ProductAdditionData): Result<Product> {
        return productOverviewRepository.addProduct(productData)
    }
}