package com.eshop.product_data.repository

import com.eshop.core.data.mapper.toProduct
import com.eshop.core.data.mapper.toShop
import com.eshop.core.domain.models.Product
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.core.util.handleApiError
import com.eshop.product_data.mapper.toCartItem
import com.eshop.product_data.remote.ProductApi
import com.eshop.product_data.remote.request.AddProductToCartRequest
import com.eshop.product_domain.model.CartItem
import com.eshop.product_domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
): ProductRepository {
    override suspend fun fetchProduct(productId: String): Result<Product> {
        return try {
            val result = productApi.fetchProduct(productId)
            Result.Success(result.toProduct())
        } catch (e: Exception) {
            handleApiError(e)
        }
    }

    override suspend fun fetchProductOwner(shopIdentifier: String): Result<Shop> {
        return try {
            val result = productApi.fetchShop(shopIdentifier)
            Result.Success(result.toShop())
        } catch (e: Exception) {
            handleApiError(e)
        }
    }

    override suspend fun addProductToCart(productId: String): Result<CartItem> {
        return try {
            val result = productApi.addProductToCart(AddProductToCartRequest(productId))
            Result.Success(result.toCartItem())
        } catch (e: Exception) {
            handleApiError(e)
        }
    }
}