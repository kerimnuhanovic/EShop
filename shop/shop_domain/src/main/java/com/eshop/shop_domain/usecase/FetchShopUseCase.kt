package com.eshop.shop_domain.usecase

import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.shop_domain.repository.ShopRepository
import javax.inject.Inject

class FetchShopUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(shopId: String): Result<Shop> {
        return shopRepository.fetchShop(shopId)
    }
}