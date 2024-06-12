package com.eshop.userdashboard_presentation

import com.eshop.coreui.util.BottomBarItem
import com.eshop.userdashboard_presentation.util.DashboardItem

data class UserDashboardState(
    val items: List<DashboardItem> = emptyList(),
    val bottomBarItems: List<BottomBarItem> = emptyList()
)