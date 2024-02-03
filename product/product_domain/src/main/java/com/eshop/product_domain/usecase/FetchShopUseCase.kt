package com.eshop.product_domain.usecase

import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.product_domain.repository.ProductRepository
import javax.inject.Inject

class FetchShopUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(shopIdentifier: String): Result<Shop> {
        return productRepository.fetchProductOwner(shopIdentifier)
    }
}