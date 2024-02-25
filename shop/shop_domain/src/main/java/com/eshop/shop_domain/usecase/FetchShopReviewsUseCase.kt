package com.eshop.shop_domain.usecase

import com.eshop.core.util.Result
import com.eshop.shop_domain.model.AllReviews
import com.eshop.shop_domain.repository.ShopRepository
import javax.inject.Inject

class FetchShopReviewsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(shopId: String): Result<AllReviews> {
        return shopRepository.fetchShopReviews(shopId)
    }
}