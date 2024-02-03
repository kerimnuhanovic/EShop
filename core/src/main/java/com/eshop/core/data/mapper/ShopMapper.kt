package com.eshop.core.data.mapper

import com.eshop.core.data.remote.dto.ShopDto
import com.eshop.core.domain.models.Shop
import com.eshop.core.util.UserType

fun ShopDto.toShop() = Shop(
    this.id,
    this.name,
    this.surname,
    this.username,
    this.email,
    this.profileImage,
    UserType.fromString(this.userType),
    this.shopCategories,
    this.shopLocations
)