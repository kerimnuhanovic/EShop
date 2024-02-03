package com.eshop.core.domain.models

import com.eshop.core.util.UserType

data class Shop(
    val id: String,
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val profileImage: String,
    val userType: UserType,
    val shopCategories: List<String>,
    val shopLocations: List<String>
)
