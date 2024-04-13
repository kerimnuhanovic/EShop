package com.eshop.shopoverview_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.core.domain.models.FilteredShopAndProductCategory
import com.eshop.coreui.navigation.Route
import com.eshop.core.util.Result
import com.eshop.coreui.util.UiEvent
import com.eshop.shopoverview_domain.usecase.FetchAllShopsUseCase
import com.eshop.shopoverview_domain.usecase.FetchPopularShopsUseCase
import com.eshop.coreui.util.SelectedCategory
import com.eshop.coreui.util.SelectedSortCriterion
import com.eshop.coreui.util.generateSortCriteriaForShops
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopOverviewViewModel @Inject constructor(
    private val fetchAllShopsUseCase: FetchAllShopsUseCase,
    private val fetchPopularShopsUseCase: FetchPopularShopsUseCase
) : ViewModel() {

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
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        isSearchBarVisible = true
                    )
                    delay(100L)
                    _uiEvent.send(UiEvent.FocusInputField)
                }
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
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.ChangeNavigationDrawerState)
                }
            }

            ShopOverviewEvent.OnSearch -> {
                onSearch()
            }

            ShopOverviewEvent.OnFilterDrawerItemClick -> {
                _state.value = state.value.copy(
                    isFilterDrawerItemExpanded = !state.value.isFilterDrawerItemExpanded
                )
            }

            is ShopOverviewEvent.OnShopCategorySelect -> {
                _state.value = state.value.copy(
                    shopCategories = state.value.shopCategories.map {
                        if (it == event.selectedCategory) {
                            SelectedCategory(it.category, !it.isSelected)
                        } else {
                            it
                        }
                    }
                )
            }

            ShopOverviewEvent.OnSortDrawerItemClick -> {
                _state.value = state.value.copy(
                    isSortDrawerItemExpanded = !state.value.isSortDrawerItemExpanded
                )
            }

            is ShopOverviewEvent.OnSortCriterionSelect -> {
                _state.value = state.value.copy(
                    sortCriteria = state.value.sortCriteria.map {
                        if (it == event.sortCriterion) {
                            SelectedSortCriterion(it.criterion, !it.isSelected)
                        } else {
                            SelectedSortCriterion(it.criterion, false)
                        }
                    }
                )
            }

            ShopOverviewEvent.OnFilterClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.ChangeNavigationDrawerState)
                }
                fetchFilteredShops()
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

    private suspend fun fetchAllShops(
        offset: Int,
        searchQuery: String? = null,
        filteredCategories: List<SelectedCategory>? = null,
        sortCriterion: SelectedSortCriterion = generateSortCriteriaForShops().first()
    ) {
        when (val result = fetchAllShopsUseCase(
            offset,
            searchQuery,
            filteredCategories?.map { FilteredShopAndProductCategory(it.category.value) },
            sortCriterion.criterion.fieldName,
            sortCriterion.criterion.orderBy.value
        )
        ) {
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
            fetchAllShops(
                offset = state.value.allShops.size,
                searchQuery = state.value.searchQuery,
                filteredCategories = state.value.shopCategories.filter { category -> category.isSelected },
                sortCriterion = state.value.sortCriteria.find { criterion -> criterion.isSelected }.let {
                    it ?: generateSortCriteriaForShops().first()
                }
            )
            _state.value = _state.value.copy(
                isLoadingMoreShops = false
            )
        }
    }

    private fun onSearch() {
        if (state.value.searchQuery.isEmpty()) {
            fetchInitialShops()
        } else {
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
            fetchAllShops(
                offset = state.value.allShops.size,
                searchQuery = state.value.searchQuery,
                filteredCategories = state.value.shopCategories.filter { category -> category.isSelected },
                sortCriterion = state.value.sortCriteria.find { criterion -> criterion.isSelected }.let {
                    it ?: generateSortCriteriaForShops().first()
                }
            )
        }
    }

    private fun fetchFilteredShops() {
        _state.value = _state.value.copy(
            isAllShopsLoading = true,
            allShops = emptyList()
        )
        viewModelScope.launch {
            fetchAllShops(
                offset = state.value.allShops.size,
                searchQuery = state.value.searchQuery,
                filteredCategories = state.value.shopCategories.filter { category -> category.isSelected },
                sortCriterion = state.value.sortCriteria.find { criterion -> criterion.isSelected }.let {
                    it ?: generateSortCriteriaForShops().first()
                }
            )
        }
    }
}