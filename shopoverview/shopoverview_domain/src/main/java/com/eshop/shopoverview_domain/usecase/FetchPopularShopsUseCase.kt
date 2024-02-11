package com.eshop.shopoverview_domain.usecase

import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.shopoverview_domain.repository.ShopOverviewRepository
import javax.inject.Inject

class FetchPopularShopsUseCase @Inject constructor(
    private val shopOverviewRepository: ShopOverviewRepository
) {
    suspend operator fun invoke(): Result<List<Shop>> {
        return shopOverviewRepository.fetchPopularShops()
    }
}