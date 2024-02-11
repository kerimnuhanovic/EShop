package com.eshop.productoverview_domain.repository

import com.eshop.core.domain.models.Product
import com.eshop.core.util.Result
import com.eshop.productoverview_domain.model.ProductAdditionData

interface ProductOverviewRepository {
    suspend fun addProduct(productData: ProductAdditionData): Result<Product>
    suspend fun fetchPopularProducts(): Result<List<Product>>
    suspend fun fetchAllProducts(offset: Int): Result<List<Product>>
}