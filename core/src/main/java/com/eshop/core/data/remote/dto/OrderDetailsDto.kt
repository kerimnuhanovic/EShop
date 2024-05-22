package com.eshop.core.data.remote.dto

data class OrderDetailsDto(val id: String?, val shop: String, val items: List<String>, val status: String)
