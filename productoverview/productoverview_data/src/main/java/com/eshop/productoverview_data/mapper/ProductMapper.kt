package com.eshop.productoverview_data.mapper

import com.eshop.core.data.remote.dto.ProductDto
import com.eshop.core.domain.models.Product
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ProductDto.toProduct(): Product {
    val date = this.date.split("T")[0]
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateProductIsAdded = LocalDate.parse(date, formatter)
    return Product(
        this.id,
        this.title,
        this.description,
        this.category,
        this.price,
        this.shop,
        dateProductIsAdded,
        this.images
    )
}