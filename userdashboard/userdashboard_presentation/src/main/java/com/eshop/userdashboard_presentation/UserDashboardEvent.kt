package com.eshop.userdashboard_presentation

import com.eshop.coreui.navigation.Route

sealed interface UserDashboardEvent {
    data class OnItemClick(val route: Route) : UserDashboardEvent
}