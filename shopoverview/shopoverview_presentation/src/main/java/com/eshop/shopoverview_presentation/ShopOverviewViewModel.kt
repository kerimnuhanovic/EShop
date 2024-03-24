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
                    isSearchBarVisible = true
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
            ShopOverviewEvent.OnDeleteSearchTextClick -> {
                _state.value = _state.value.copy(
                    searchQuery = ""
                )
            }
            ShopOverviewEvent.OnExitSearchBarClick -> {
                _state.value = _state.value.copy(
                    searchQuery = "",
                    isSearchBarVisible = false
                )
            }

            ShopOverviewEvent.OnFilterIconClick -> {
                // TODO
            }

            ShopOverviewEvent.OnSearch -> {
                onSearch()
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

    private suspend fun fetchAllShops(offset: Int, searchQuery: String? = null) {
        when (val result = fetchAllShopsUseCase(offset, searchQuery)) {
            is Result.Success -> {
                _state.value = _state.value.copy(
                    isAllShopsLoading = false,
                    allShops = state.value.allShops + result.data,
                    areAllShopsLoaded = result.data.isEmpty(),
                    areSearchedShopsDisplayed = !searchQuery.isNullOrEmpty()
                )
            }
            is Result.Failure -> {
                // TODO
            }
        }
    }


    private fun fetchInitialShops() {
        _state.value = _state.value.copy(
            isSearchBarVisible = false,
            isPopularShopsLoading = true,
            isAllShopsLoading = true,
            areSearchedShopsDisplayed = false,
            searchedQuery = ""
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
            fetchAllShops(offset = state.value.allShops.size, searchQuery = state.value.searchQuery)
            _state.value = _state.value.copy(
                isLoadingMoreShops = false
            )
        }
    }

    private fun onSearch() {
        if (state.value.searchQuery.isEmpty()) {
            fetchInitialShops()
        }
        else {
            fetchSearchedShops()
        }
    }

    private fun fetchSearchedShops() {
        _state.value = state.value.copy(
            isAllShopsLoading = true,
            allShops = emptyList(),
            isSearchBarVisible = false,
            searchedQuery = state.value.searchQuery
        )
        viewModelScope.launch {
            fetchAllShops(state.value.allShops.size, searchQuery = state.value.searchQuery)
        }
    }
}