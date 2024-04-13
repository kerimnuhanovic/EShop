package com.eshop.coreui.util

import com.eshop.coreui.R


enum class OrderBy(val value: String) {
    DESC("desc"),
    ASC("asc")
}

data class SortCriterion(val fieldName: String, val orderBy: OrderBy, val labelId: Int)

data class SelectedSortCriterion(val criterion: SortCriterion, val isSelected: Boolean)

fun generateSortCriteriaForShops(): List<SelectedSortCriterion> = listOf(
    SelectedSortCriterion(SortCriterion(fieldName = "username", OrderBy.ASC, R.string.sort_by_shop_name_asc), false),
    SelectedSortCriterion(SortCriterion(fieldName = "username", OrderBy.DESC, R.string.sort_by_shop_name_desc), false),
)

fun generateSortCriteriaForProducts(): List<SelectedSortCriterion> = listOf(
    SelectedSortCriterion(SortCriterion(fieldName = "title", OrderBy.ASC, R.string.sort_by_product_name_asc), false),
    SelectedSortCriterion(SortCriterion(fieldName = "title", OrderBy.DESC, R.string.sort_by_product_name_desc), false),
    SelectedSortCriterion(SortCriterion(fieldName = "price", OrderBy.DESC, R.string.sort_by_product_price_desc), false),
    SelectedSortCriterion(SortCriterion(fieldName = "price", OrderBy.ASC, R.string.sort_by_product_price_asc), false),
    SelectedSortCriterion(SortCriterion(fieldName = "date", OrderBy.DESC, R.string.sort_by_product_date_desc), false),
    SelectedSortCriterion(SortCriterion(fieldName = "price", OrderBy.ASC, R.string.sort_by_product_date_asc), false),
)
