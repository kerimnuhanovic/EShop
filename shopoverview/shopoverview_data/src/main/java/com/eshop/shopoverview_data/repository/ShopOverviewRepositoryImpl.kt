package com.eshop.shopoverview_data.repository

import com.eshop.core.data.mapper.toShop
import com.eshop.core.domain.models.FilteredShopAndProductCategory
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.core.util.handleApiError
import com.eshop.shopoverview_data.remote.ShopOverviewApi
import com.eshop.shopoverview_domain.repository.ShopOverviewRepository
import java.lang.Exception
import javax.inject.Inject

class ShopOverviewRepositoryImpl @Inject constructor(
    private val shopOverviewApi: ShopOverviewApi
) : ShopOverviewRepository {
    override suspend fun fetchAllShops(
        offset: Int,
        searchQuery: String?,
        filteredCategories: List<FilteredShopAndProductCategory>?,
        sortBy: String?,
        orderBy: String?
    ): Result<List<Shop>> {
        return try {
            val result = shopOverviewApi.fetchAllShops(
                offset,
                searchQuery,
                filteredCategories?.joinToString(separator = "$") { it.category },
                sortBy,
                orderBy
            )
            val shops = result.map { shopDto ->
                shopDto.toShop()
            }
            Result.Success(shops)
        } catch (e: Exception) {
            handleApiError(e)
        }
    }

    override suspend fun fetchPopularShops(): Result<List<Shop>> {
        return try {
            val result = shopOverviewApi.fetchPopularShops()
            val shops = result.map { shopDto ->
                shopDto.toShop()
            }
            Result.Success(shops)
        } catch (e: Exception) {
            handleApiError(e)
        }
    }
}