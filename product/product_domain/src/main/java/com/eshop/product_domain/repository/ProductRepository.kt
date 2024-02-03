package com.eshop.product_domain.repository

import com.eshop.core.domain.models.Product
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.Result
import com.eshop.product_domain.model.CartItem

interface ProductRepository {
    suspend fun fetchProduct(productId: String): Result<Product>
    suspend fun fetchProductOwner(shopIdentifier: String): Result<Shop>
    suspend fun addProductToCart(productId: String): Result<CartItem>
}