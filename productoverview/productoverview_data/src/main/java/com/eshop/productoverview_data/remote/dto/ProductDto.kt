package com.eshop.productoverview_data.remote.dto

data class ProductDto(
    val id: String,
    val title: String,
    val description: String,
    val category: List<String>,
    val price: Double,
    val shop: String,
    val date: String,
    val images: List<String>
)
