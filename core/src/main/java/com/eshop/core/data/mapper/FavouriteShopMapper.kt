package com.eshop.core.data.mapper

import com.eshop.core.data.remote.dto.FavouriteShopDto
import com.eshop.core.domain.models.FavouriteShop
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun FavouriteShopDto.toFavouriteShop(): FavouriteShop {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateDocumentIsCreated = LocalDate.parse(this.dateCreated, inputFormatter)
    return FavouriteShop(
        id = id,
        userId = userId,
        shopId = shopId,
        dateCreated = dateDocumentIsCreated
    )
}