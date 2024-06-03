package com.eshop.core.data.repository

import com.eshop.core.data.mapper.toFavouriteProduct
import com.eshop.core.data.mapper.toProduct
import com.eshop.core.data.remote.FavouriteProductApi
import com.eshop.core.domain.models.FavouriteProduct
import com.eshop.core.domain.models.Product
import com.eshop.core.domain.repository.FavouriteProductRepository
import com.eshop.core.util.Result
import com.eshop.core.util.handleApiError
import javax.inject.Inject

class FavouriteProductRepositoryImpl @Inject constructor(
    private val favouriteProductApi: FavouriteProductApi
) : FavouriteProductRepository {
    override suspend fun addProduct(productId: String): Result<FavouriteProduct> {
        return try {
            val result = favouriteProductApi.addProduct(productId)
            Result.Success(result.toFavouriteProduct())
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun listProducts(): Result<List<Product>> {
        return try {
            val result = favouriteProductApi.listProducts()
            val products = result.map { productDto ->
                productDto.toProduct()
            }
            Result.Success(products)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun deleteProduct(productId: String): Result<Unit> {
        return try {
            favouriteProductApi.deleteProduct(productId)
            Result.Success(Unit)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun checkIsProductFavourite(productId: String): Result<Boolean> {
        return try {
            val result = favouriteProductApi.isProductFavourite(productId)
            Result.Success(result)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}