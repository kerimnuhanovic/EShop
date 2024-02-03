package com.eshop.product_presentation

import com.eshop.core.domain.models.Product
import com.eshop.core.domain.models.Shop

data class ProductState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val productOwner: Shop? = null,
    val isAddingProductInProgress: Boolean = false
)