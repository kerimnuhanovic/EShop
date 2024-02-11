package com.eshop.core.util

sealed interface UserType {
    object Shop : UserType
    object Customer : UserType
    companion object {
        fun fromString(userType: String): UserType =
            when {
                userType == "Shop" -> Shop
                userType == "Customer" -> Customer
                else -> throw IllegalArgumentException("Function called with invalid user type!")
            }

    }
}