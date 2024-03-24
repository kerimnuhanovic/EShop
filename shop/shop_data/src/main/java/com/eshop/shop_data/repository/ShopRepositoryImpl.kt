package com.eshop.shop_data.repository

import com.eshop.core.data.mapper.toProduct
import com.eshop.core.data.mapper.toShop
import com.eshop.core.domain.models.Product
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.core.util.handleApiError
import com.eshop.shop_data.mapper.toAllReviews
import com.eshop.shop_data.remote.ShopApi
import com.eshop.shop_domain.model.AllReviews
import com.eshop.shop_domain.repository.ShopRepository
import java.lang.Exception
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopApi: ShopApi
) : ShopRepository {
    override suspend fun fetchShop(id: String): Result<Shop> {
        return try {
            val result = shopApi.fetchShop(id)
            Result.Success(result.toShop())
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun fetchShopProducts(shopId: String): Result<List<Product>> {
        return try {
            val result = shopApi.fetchShopProducts(shopId)
            val products = result.map {
                it.toProduct()
            }
            Result.Success(products)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun fetchShopReviews(shopId: String): Result<AllReviews> {
        return try {
            val result = shopApi.fetchShopReviews(shopId)
            Result.Success(result.toAllReviews())
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}