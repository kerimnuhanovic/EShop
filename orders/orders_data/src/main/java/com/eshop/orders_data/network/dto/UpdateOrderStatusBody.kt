package com.eshop.orders_data.network.dto

data class UpdateOrderStatusBody(val id: String, val orderDetailsId: String, val status: String)
