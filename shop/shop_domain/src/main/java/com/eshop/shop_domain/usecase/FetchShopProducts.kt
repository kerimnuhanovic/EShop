package com.eshop.shop_domain.usecase

import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result
import com.eshop.shop_domain.repository.ShopRepository
import javax.inject.Inject

class FetchShopProducts @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(shopId: String): Result<List<Product>> {
        return shopRepository.fetchShopProducts(shopId)
    }
}