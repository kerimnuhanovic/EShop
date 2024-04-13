package com.eshop.shopoverview_domain.usecase

import com.eshop.core.domain.models.FilteredShopAndProductCategory
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.shopoverview_domain.repository.ShopOverviewRepository
import javax.inject.Inject

class FetchAllShopsUseCase @Inject constructor(
    private val shopOverviewRepository: ShopOverviewRepository
) {
    suspend operator fun invoke(
        offset: Int,
        searchQuery: String? = null,
        filteredCategories: List<FilteredShopAndProductCategory>? = null,
        sortBy: String? = null,
        orderBy: String? = null
    ): Result<List<Shop>> {
        return shopOverviewRepository.fetchAllShops(offset, searchQuery, filteredCategories, sortBy, orderBy)
    }
}