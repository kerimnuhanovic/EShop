package com.eshop.core.domain.repository

import com.eshop.core.domain.models.FavouriteProduct
import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result

interface FavouriteProductRepository {
    suspend fun addProduct(productId: String): Result<FavouriteProduct>
    suspend fun listProducts(): Result<List<Product>>
    suspend fun deleteProduct(productId: String): Result<Unit>
    suspend fun checkIsProductFavourite(productId: String): Result<Boolean>
}