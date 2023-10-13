package com.eshop.signup_presentation.signup.util

sealed class ShopCategory(val category: String) : ShopData(category) {
    object ApparelAndFashion : ShopCategory("Apparel and Fashion")
    object Electronics : ShopCategory("Electronics")
    object HomeAndFurniture : ShopCategory("Home and Furniture")
    object BooksAndMedia : ShopCategory("Books and Media")
    object ToysAndGames : ShopCategory("Toys and Games")
    object HealthAndBeauty : ShopCategory("Health and Beauty")
    object SportsAndOutdoors : ShopCategory("Sports and Outdoors")
    object ArtAndCraft : ShopCategory("Art and Craft")
    object Cars : ShopCategory("Cars")

    companion object {
        fun listAllCategories(): List<ShopCategory> =
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