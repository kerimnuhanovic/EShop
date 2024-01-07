package com.eshop.productoverview_domain.model

import java.io.File

data class ProductAdditionData(
    val title: String,
    val description: String,
    val categories: String,
    val price: Double,
    val images: List<File>
)
