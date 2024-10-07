package com.eshop.product_presentation

import com.eshop.core.domain.models.Product
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.UserType

data class ProductState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val productOwner: Shop? = null,
    val isAddingProductInProgress: Boolean = false,
    val isProductFavourite: Boolean = false,
    val userType: UserType = UserType.Customer
)