package com.eshop.productoverview_domain.model

import java.time.LocalDate

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val shop: String,
    val date: LocalDate,
    val images: List<String>
)
