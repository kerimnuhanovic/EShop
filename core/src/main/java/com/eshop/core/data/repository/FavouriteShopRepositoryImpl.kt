package com.eshop.core.data.repository

import com.eshop.core.data.mapper.toFavouriteShop
import com.eshop.core.data.mapper.toShop
import com.eshop.core.data.remote.FavouriteShopApi
import com.eshop.core.domain.models.FavouriteShop
import com.eshop.core.domain.models.Shop
import com.eshop.core.domain.repository.FavouriteShopRepository
import com.eshop.core.util.Result
import com.eshop.core.util.handleApiError
import javax.inject.Inject

class FavouriteShopRepositoryImpl @Inject constructor(
    private val favouriteShopApi: FavouriteShopApi
) : FavouriteShopRepository {
    override suspend fun addShop(shopId: String): Result<FavouriteShop> {
        return try {
            val result = favouriteShopApi.addShop(shopId)
            Result.Success(result.toFavouriteShop())
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun listShops(): Result<List<Shop>> {
        return try {
            val result = favouriteShopApi.listShops()
            val shops = result.map { shopDto ->
                shopDto.toShop()
            }
            Result.Success(shops)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun deleteShop(shopId: String): Result<Unit> {
        return try {
            favouriteShopApi.deleteShop(shopId)
            Result.Success(Unit)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun checkIsShopFavourite(shopId: String): Result<Boolean> {
        return try {
            val result = favouriteShopApi.isShopFavourite(shopId)
            Result.Success(result)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}