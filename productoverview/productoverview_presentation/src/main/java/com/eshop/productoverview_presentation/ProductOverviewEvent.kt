package com.eshop.productoverview_presentation

import android.net.Uri
import com.eshop.coreui.util.ShopAndProductCategory

sealed interface ProductOverviewEvent {
    object OnSearchIconClick : ProductOverviewEvent
    data class OnSearchQueryEnter(val query: String) : ProductOverviewEvent
    object OnSearch : ProductOverviewEvent
    object OnAddProductClick : ProductOverviewEvent
    data class OnProductTitleEnter(val title: String) : ProductOverviewEvent
    object OnExitSearchBarClick : ProductOverviewEvent
    object OnDeleteSearchTextClick : ProductOverviewEvent
    object OnFilterIconClick : ProductOverviewEvent
    data class OnProductDescriptionEnter(val description: String) : ProductOverviewEvent
    data class OnProductPriceEnter(val price: String) : ProductOverviewEvent
    object OnExpandChange : ProductOverviewEvent
    data class OnProductCategoryClick(val productCategory: ShopAndProductCategory) : ProductOverviewEvent
    data class OnProductPhotosSelect(val photos: List<Uri>) : ProductOverviewEvent
    data class OnProductPhotoRemove(val photo: Uri) : ProductOverviewEvent
    object OnScreenEndReach : ProductOverviewEvent
    data class OnProductClick(val productId: String) : ProductOverviewEvent
}
