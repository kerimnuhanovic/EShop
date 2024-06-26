package com.eshop.cart_presentation

import com.eshop.core.domain.models.Product
import com.eshop.coreui.util.BottomBarItem

data class CartState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val subTotal: Double = 0.0,
    val deliveryCharge: Double = 10.00,
    val total: Double = 0.0,
    val isOrderSubmitting: Boolean = false,
    val bottomBarItems: List<BottomBarItem> = emptyList()
)
