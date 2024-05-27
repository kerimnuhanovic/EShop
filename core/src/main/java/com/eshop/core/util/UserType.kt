package com.eshop.core.util

sealed class UserType(val type: String) {
    object Shop : UserType("Shop")
    object Customer : UserType("Customer")
    companion object {
        fun fromString(userType: String): UserType =
            when (userType) {
                "Shop" -> Shop
                "Customer" -> Customer
                else -> throw IllegalArgumentException("Function called with invalid user type!")
            }

    }
}