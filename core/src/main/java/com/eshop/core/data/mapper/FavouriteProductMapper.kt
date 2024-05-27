package com.eshop.core.data.mapper

import com.eshop.core.data.remote.dto.FavouriteProductDto
import com.eshop.core.domain.models.FavouriteProduct
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun FavouriteProductDto.toFavouriteProduct(): FavouriteProduct {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateDocumentIsCreated = LocalDate.parse(this.dateCreated, inputFormatter)
    return FavouriteProduct(
        id = id,
        userId = userId,
        productId = productId,
        dateCreated = dateDocumentIsCreated
    )
}