package com.eshop.core.data.remote.dto

data class ShopDto(
    val id: String,
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val profileImage: String,
    val userType: String,
    val shopCategories: List<String>,
    val shopLocations: List<String>
)
