package com.eshop.userdashboard_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.util.UserType
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.util.UiEvent
import com.eshop.coreui.util.generateBottomBarItems
import com.eshop.userdashboard_presentation.util.DashboardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDashboardViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    private val _state: MutableStateFlow<UserDashboardState> = MutableStateFlow(UserDashboardState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        generateBarItems()
        generateDashboardItems()
    }

    fun onEvent(event: UserDashboardEvent) {
        when (event) {
            is UserDashboardEvent.OnItemClick -> {
                if (event.route != Route.LOGIN) {
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Navigate(event.route))
                    }
                }
                else {
                    logoutUser(event.route)
                }
            }
        }
    }

    private fun generateDashboardItems() {
        when (preferences.readUserType()) {
            UserType.Customer -> {
                _state.value = state.value.copy(
                    items = listOf(DashboardItem.FavouriteProducts, DashboardItem.FavouriteShops, DashboardItem.MyOrders, DashboardItem.LogOut)
                )
            }
            UserType.Shop -> {
                _state.value = state.value.copy(
                    items = listOf(DashboardItem.FavouriteProducts, DashboardItem.FavouriteShops, DashboardItem.LogOut)
                )
            }
            null -> {
                // no-op
            }
        }
    }

    private fun logoutUser(route: String) {
        viewModelScope.launch {
            preferences.deleteToken()
            _uiEvent.send(UiEvent.Navigate(route))
        }
    }

    private fun generateBarItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                bottomBarItems = generateBottomBarItems(preferences.readUserType()!!.type)
            )
        }
    }
}