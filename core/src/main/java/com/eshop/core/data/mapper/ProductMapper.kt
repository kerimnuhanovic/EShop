package com.eshop.core.data.mapper

import com.eshop.core.data.remote.dto.ProductDto
import com.eshop.core.domain.models.Product
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ProductDto.toProduct(): Product {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateProductIsAdded = LocalDate.parse(this.date, inputFormatter)
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