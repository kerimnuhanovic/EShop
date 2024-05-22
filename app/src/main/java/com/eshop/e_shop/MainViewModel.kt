package com.eshop.e_shop

import androidx.lifecycle.ViewModel
import com.eshop.core.domain.preferences.Preferences
import com.eshop.coreui.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    private val _state: MutableStateFlow<AppState> = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        checkIsUserLoggedIn()
    }

    private fun checkIsUserLoggedIn() {
        if (preferences.readToken() !== null) {
            _state.value = state.value.copy(
                isUserLoggedIn = true,
                startDestination = Route.PRODUCTS_OVERVIEW
            )
        }
    }

}