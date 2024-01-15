package com.eshop.coreui.util

sealed class ShopAndProductCategory(val category: String) : ItemData(category) {
    object ApparelAndFashion : ShopAndProductCategory("Apparel and Fashion")
    object Electronics : ShopAndProductCategory("Electronics")
    object HomeAndFurniture : ShopAndProductCategory("Home and Furniture")
    object BooksAndMedia : ShopAndProductCategory("Books and Media")
    object ToysAndGames : ShopAndProductCategory("Toys and Games")
    object HealthAndBeauty : ShopAndProductCategory("Health and Beauty")
    object SportsAndOutdoors : ShopAndProductCategory("Sports and Outdoors")
    object ArtAndCraft : ShopAndProductCategory("Art and Craft")
    object Cars : ShopAndProductCategory("Cars")

    companion object {
        fun listAllCategories(): List<ShopAndProductCategory> =
            listOf(
                ApparelAndFashion,
                Electronics,
                HomeAndFurniture,
                BooksAndMedia,
                ToysAndGames,
                HealthAndBeauty,
                SportsAndOutdoors,
                ArtAndCraft,
                Cars
            )
    }
}