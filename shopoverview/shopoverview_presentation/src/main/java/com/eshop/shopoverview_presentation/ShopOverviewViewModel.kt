package com.eshop.shopoverview_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.navigation.Route
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import com.eshop.shopoverview_domain.usecase.FetchAllShopsUseCase
import com.eshop.shopoverview_domain.usecase.FetchPopularShopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopOverviewViewModel @Inject constructor(
    private val fetchAllShopsUseCase: FetchAllShopsUseCase,
    private val fetchPopularShopsUseCase: FetchPopularShopsUseCase
): ViewModel() {

    private val _state: MutableStateFlow<ShopOverviewState> = MutableStateFlow(ShopOverviewState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchInitialShops()
    }

    fun onEvent(event: ShopOverviewEvent) {
        when (event) {
            ShopOverviewEvent.OnScreenEndReach -> {
                loadMoreShops()
            }
            ShopOverviewEvent.OnSearchIconClick -> {
                _state.value = _state.value.copy(
                    isSearchBarExpanded = !state.value.isSearchBarExpanded
                )
            }
            is ShopOverviewEvent.OnSearchQueryEnter -> {
                _state.value = _state.value.copy(
                    searchQuery = event.searchQuery
                )
            }
            is ShopOverviewEvent.OnShopClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("${Route.SHOP}/${event.shopId}"))
                }
            }
        }
    }

    private suspend fun fetchPopularShops() {
        when (val result = fetchPopularShopsUseCase()) {
            is Result.Success -> {
                _state.value = _state.value.copy(
                    isPopularShopsLoading = false,
                    popularShops = result.data
                )
            }
            is Result.Failure -> {
                // TODO
            }
        }
    }

    private suspend fun fetchAllShops(offset: Int) {
        when (val result = fetchAllShopsUseCase(offset)) {
            is Result.Success -> {
                _state.value = _state.value.copy(
                    isAllShopsLoading = false,
                    allShops = state.value.allShops + result.data,
                    areAllShopsLoaded = result.data.isEmpty()
                )
            }
            is Result.Failure -> {
                // TODO
            }
        }
    }


    private fun fetchInitialShops() {
        _state.value = _state.value.copy(
            isPopularShopsLoading = true,
            isAllShopsLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                fetchPopularShops()
            }
            launch {
                fetchAllShops(state.value.allShops.size)
            }
        }
    }

    private fun loadMoreShops() {
        if (state.value.areAllShopsLoaded || state.value.isLoadingMoreShops) return
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoadingMoreShops = true
            )
            fetchAllShops(state.value.allShops.size)
            _state.value = _state.value.copy(
                isLoadingMoreShops = false
            )
        }
    }

}