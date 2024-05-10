package com.eshop.cart_domain.repository

import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result

interface CartRepository {
    suspend fun fetchCartItems(): Result<List<Product>>
}