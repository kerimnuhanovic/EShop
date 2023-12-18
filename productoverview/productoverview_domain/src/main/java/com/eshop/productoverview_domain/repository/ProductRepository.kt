package com.eshop.productoverview_domain.repository

import com.eshop.productoverview_domain.model.Product
import com.eshop.core.util.Result
import com.eshop.productoverview_domain.model.ProductAdditionData

interface ProductRepository {
    suspend fun addProduct(productData: ProductAdditionData): Result<Product>
}