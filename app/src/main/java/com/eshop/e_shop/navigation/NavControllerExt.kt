package com.eshop.e_shop.navigation

import androidx.navigation.NavController
import com.eshop.coreui.util.UiEvent

fun NavController.navigate(uiEvent: UiEvent.Navigate) {
    this.navigate(uiEvent.route)
}