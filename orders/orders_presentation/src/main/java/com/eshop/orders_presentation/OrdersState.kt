package com.eshop.orders_presentation

import com.eshop.core.domain.models.OrderWithProducts
import com.eshop.core.util.UserType
import com.eshop.coreui.util.BottomBarItem

data class OrdersState(
    val orders: List<OrderWithProducts> = emptyList(),
    val bottomBarItems: List<BottomBarItem> = emptyList(),
    val userType: UserType? = null,
    val isLoading: Boolean = false,
    val expandedItems: List<Boolean> = emptyList(),
    val isAcceptStatusSubmitting: Boolean = false,
    val isDeclineStatusSubmitting: Boolean = false
)
