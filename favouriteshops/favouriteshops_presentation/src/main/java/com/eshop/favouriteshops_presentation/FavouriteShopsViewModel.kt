package com.eshop.favouriteshops_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.usecase.GetFavouriteShopsUseCase
import com.eshop.core.util.Result
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteShopsViewModel @Inject constructor(
    private val getFavouriteShopsUseCase: GetFavouriteShopsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<FavouriteShopsState> = MutableStateFlow(
        FavouriteShopsState()
    )
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchFavouriteProducts()
    }

    fun onEvent(event: FavouriteShopsEvent) {
        when (event) {
            FavouriteShopsEvent.OnNavigateBack -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }

            is FavouriteShopsEvent.OnShopClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("${Route.SHOP}/${event.shopId}"))
                }
            }
        }
    }

    private fun fetchFavouriteProducts() {
        _state.value = state.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getFavouriteShopsUseCase()) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        shops = result.data
                    )
                }
                is Result.Failure -> TODO()
            }
        }
    }
}